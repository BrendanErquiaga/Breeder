"use strict";

var chickenRowBlock = '<div class="row"></row>',
  chickenBlock = '<div class="chickenContainer" onclick="chickenContainerSelected(this)"><div class="chicken"><div class="wing"></div><div class="eye"></div><div class="beak_top"></div><div class="crest crest1"></div><div class="crest crest2"></div><div class="crest crest3"></div><div class="wattle"></div></div></div>"',
  expectedChickenBlock = '<div class="chickenContainer" onclick="chickenContainerSelected(this)"><div class="egg"></div></div>',
  getChickenURL = "https://du1ejfbfoe.execute-api.us-west-2.amazonaws.com/DEV/organism/",
  getGenepoolURL= "https://oy0br9jib0.execute-api.us-west-2.amazonaws.com/DEV/",
  breedGenepoolPath= "/breed",
  cullGenepoolPath= "/cull",
  loadedChickens = [],
  expectedChildren = [],
  generationCount = 0,
  lastGenerationBlockId = "",
  breedLongDelay = 30000,
  breedShortDelay = 10000,
  hasBred = false,
  genepoolID, genepoolObject;

$(document).ready(function() {
    pageLoad();
});

function pageLoad() {
  attemptToLoadGenepool();
}

function attemptToLoadGenepool() {
  var urlParams = new URLSearchParams(window.location.search);

  if(urlParams.has('genepoolID')) {
    genepoolID = urlParams.get('genepoolID');
    fetchGenepool(true);
  } else {
    console.warn("No genepool present this page won't do anything... Probably add some helper text");
  }
}

function fetchGenepool(shouldLoadChickens) {
    var request = $.get(getGenepoolURL + genepoolID, function(data) {
      genepoolFetched(data, shouldLoadChickens);
    });

    request.fail(function() {
      console.error("Error fetching genepool data: " + genepoolID);
    });
}

function genepoolFetched(genepoolData, shouldLoadChickens) {
  console.log("Genepool loaded. shouldLoadChickens: " + shouldLoadChickens);
  genepoolObject = genepoolData;

  $("#genepoolId").html("ID: " + genepoolObject.id);
  $("#organismCount").html("Organisms: " + genepoolObject.organismsInGenepool.length);
  $("#generationCount").html("Generations: " + genepoolObject.genepoolGenerations.length);
  $("#timesBred").html("Breedings: " + genepoolObject.timesBred);

  if(shouldLoadChickens) {
    loadGenerations();
  }
}

function loadGenerations() {
  for (var i = 0; i < genepoolObject.genepoolGenerations.length; i++) {
    loadGeneration(genepoolObject.genepoolGenerations[i]);
    generationCount++;
  }
}

function loadGeneration(generation) {
  var newGenerationID = "generationRow_" + generationCount,
      newGenerationBlock = $(chickenRowBlock).attr('id', newGenerationID);

  newGenerationBlock.appendTo("#chickenRowHolder");

  for (var i = 0; i < generation.length; i++) {
    callGetChickenURL(generation[i], newGenerationID);
  }
}

function callGetChickenURL(chickenID, newGenerationID) {
  var request = $.get(getChickenURL + chickenID, function(data) {
    addCustomChicken(data, newGenerationID);
  });

  request.fail(function() {
    console.error("Error fetching chicken data: " + chickenID);
  });
}

function addCustomChicken(chickenDNA, newGenerationID) {
  var newChickenID = "chicken_" + chickenDNA.id,
      newBlock = $(chickenBlock).attr('id', newChickenID);

  newBlock.appendTo("#" + newGenerationID);

  loadCustomChicken(newChickenID, getChickenObject(chickenDNA));
}

function loadCustomChicken(chickenBlockId, chicken) {
  var chickenParent = $("#" + chickenBlockId);

  $(chickenParent).find(".chicken").css("background-color", chicken.FeatherColor1);
  $(chickenParent).find(".wing").css("background-color", chicken.FeatherColor2);
  $(chickenParent).find(".beak_top").css("background-color", chicken.BeakColor);
  $(chickenParent).find(".crest").css("background-color", chicken.WattleColor);
  $(chickenParent).find(".wattle").css("background-color", chicken.WattleColor);

  // $(chickenParent).find(".chickenId").html(chicken.id);
  // $(chickenParent).find(".chickenName").html(chicken.name);

  loadedChickens.push(chicken);
}

// ~~~~~~~~~~~~~~~~~~ BREEDING ~~~~~~~~~~~~~~~~~~

function breedGenepool() {
  if(genepoolID === undefined) {
    console.warn("No GenepoolID present. Can't breed...");
    return;
  }

  disableAllButtons();
  var breedGenepoolURL = getGenepoolURL + genepoolID + breedGenepoolPath,
    request = $.post(breedGenepoolURL, function(data) {
      genepoolBredSuccessfull(data);
      fetchGenepool(false);
    });

  request.fail(function(data) {
    console.error("Error breeding genepool: " + data);
    enableAllButtons();
  });
}

function breedingFinished() {
  enableAllButtons();
  generationCount++;
}

function genepoolBredSuccessfull(responseMessage) {
  addExpectedChildren(JSON.parse(responseMessage.newChildrenIds));
  checkIfBreedingFinished();
  kickOffExpectedChildrenRequests();

  //TODO Update Genepool Stats
}

function checkIfBreedingFinished() {
  //console.log("Checking if breeding finished...");
  //console.log(expectedChildren);
  if(expectedChildren.length <= 0) {
    breedingFinished();
  }
}

function kickOffExpectedChildrenRequests() {
  for(var i = 0; i < expectedChildren.length; i++) {
    kickOffExpectedChildRequest(expectedChildren[i]);
  }

  hasBred = true;
}

function kickOffExpectedChildRequest(chickenId) {
  var currentDelay = (hasBred) ? breedShortDelay : breedLongDelay;

  //console.log("Sending request for chickenID: " + chickenId + " in " + currentDelay + "ms.");
  setTimeout(function() {
    attemptToGetExpectedChicken(chickenId);
  }, currentDelay);
}

function attemptToGetExpectedChicken(chickenID) {
  //console.log("Sending request for chickenID: " + chickenID);
  var request = $.get(getChickenURL + chickenID, function(data) {
    replaceExpectedChild(data, chickenID);
    expectedChildren.remove(chickenID);
    checkIfBreedingFinished();
  });

  request.fail(function() {
    console.log();("Error fetching expected chicken, trying again. chickenID: " + chickenID);
    kickOffExpectedChildRequest(chickenID);
  });
}

function replaceExpectedChild(chickenDNA, chickenID) {
  var chickenBlockId = "chicken_" +  chickenID;
  $("#chicken_" +  chickenID).remove();

  //console.log("Should have removed egg for: " + chickenBlockId);

  addCustomChicken(chickenDNA, "generationRow_" + generationCount);
}

function addExpectedChildren(newChildIds) {
  var newGenerationID = "generationRow_" + generationCount,
      newGenerationBlock = $(chickenRowBlock).attr('id', newGenerationID);

  newGenerationBlock.appendTo("#chickenRowHolder");

  for(var i = 0; i < newChildIds.length; i++){
    var newChickenID = "chicken_" +  newChildIds[i],
        newChickenBlock = $(expectedChickenBlock).attr('id', newChickenID);

    newChickenBlock.appendTo(newGenerationBlock);
    expectedChildren.push(newChildIds[i]);
  }
}

Array.prototype.remove = function(x) {
    var i;
    for(i in this){
        if(this[i].toString() == x.toString()){
            this.splice(i,1)
        }
    }
}

// ~~~~~~~~~~~~~~~~~~ CULLING ~~~~~~~~~~~~~~~~~~

function cullGenepool() {
  if(genepoolID === undefined) {
    console.warn("No GenepoolID present. Can't cull...");
    return;
  }

  disableAllButtons();

  var cullGenepoolURL = getGenepoolURL + genepoolID + cullGenepoolPath,
    request = $.post(cullGenepoolURL, {}, function(data) {
      genepoolCullSuccessful(data);
      fetchGenepool(false);
    }, "text");

  request.fail(function(data) {
    console.error("Error culling genepool ->");
    console.log(data);
    enableAllButtons();
  });
}

function cullingFinished() {
  enableAllButtons();
}

function genepoolCullSuccessful(responseMessage) {
  //console.log(responseMessage);
  removeChickensFromLoadedChickenArray(genepoolObject.genepoolGenerations[0]);

  $("#chickenRowHolder").children(':first-child').remove();

  cullingFinished();
  //TODO Update Genepool Stats
}

function removeChickensFromLoadedChickenArray(culledChickenArray) {
  var chickensToRemove = [];

  for(var i = 0; i < culledChickenArray.length; i++) {
    for(var j = 0; j < loadedChickens.length; j++) {
      if(loadedChickens[j].id === culledChickenArray[i]) {
        chickensToRemove.push(loadedChickens[j]);
      }
    }
  }

  loadedChickens = loadedChickens.filter(function(el) {
    return chickensToRemove.indexOf(el) < 0;
  });
}
