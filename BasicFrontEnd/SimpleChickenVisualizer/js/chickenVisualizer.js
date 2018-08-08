"use strict";

var chickenRowBlock = '<div class="row"></row>',
  chickenBlock = '<div class="chickenContainer" id="customChicken1"><div class="chicken"><div class="wing"></div><div class="eye"></div><div class="beak_top"></div><div class="crest crest1"></div><div class="crest crest2"></div><div class="crest crest3"></div><div class="wattle"></div></div><p>ID: <span class="chickenId"></span><br>Name: <span class="chickenName"></span></p></div>"',
  chickenCount = 0,
  getChickenURL = "https://du1ejfbfoe.execute-api.us-west-2.amazonaws.com/DEV/organism/",
  getGenepoolURL= "https://oy0br9jib0.execute-api.us-west-2.amazonaws.com/DEV/",
  genepoolID, genepoolObject, loadedChickens = [];

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
    fetchGenepool();
  } else {
    console.warn("No genepool present this page won't do anything... Probably add some helper text");
  }
}

function fetchGenepool() {
    var request = $.get(getGenepoolURL + genepoolID, function(data) {
      genepoolFetched(data);
    });

    request.fail(function() {
      console.error("Error fetching genepool data: " + genepoolID);
    });
}

function genepoolFetched(genepoolData) {
  genepoolObject = genepoolData;

  $("#genepoolId").html("id: " + genepoolObject.id);
  $("#organismCount").html("organisms: " + genepoolObject.organismsInGenepool.length);
  $("#generationCount").html("generations: " + genepoolObject.genepoolGenerations.length);
  $("#timesBred").html("breedings: " + genepoolObject.timesBred);

  loadGenerations();
}

function loadGenerations() {
  for (var i = 0; i < genepoolObject.genepoolGenerations.length; i++) {
    loadGeneration(genepoolObject.genepoolGenerations[i], i);
  }
}

function loadGeneration(generation, generationID) {
  var newGenerationID = "generationRow_" + generationID,
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
  var newChickenID = "chicken" + chickenCount++,
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

  $(chickenParent).find(".chickenId").html(chicken.id);
  $(chickenParent).find(".chickenName").html(chicken.name);

  loadedChickens.push(chicken);
  console.log(loadedChickens);
}
