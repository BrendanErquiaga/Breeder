"use strict";

var chickenBlock = '<div class="chickenContainer" id="customChicken1"><div class="chicken"><div class="wing"></div><div class="eye"></div><div class="beak_top"></div><div class="crest crest1"></div><div class="crest crest2"></div><div class="crest crest3"></div><div class="wattle"></div></div><p>ID: <span class="chickenId"></span><br>Name: <span class="chickenName"></span></p></div>"',
  chickenCount = 0,
  getChickenURL = "https://du1ejfbfoe.execute-api.us-west-2.amazonaws.com/DEV/organism/",
  chickenIDs = [ 0, 1, 2, 3, 4];

$(document).ready(function() {
    pageLoad();
});

function pageLoad() {
  loadChickens();
}

function loadChickens() {
  for (var i = 0; i < chickenIDs.length; i++) {
    callGetChickenURL(i);
  }

  console.log("All chickens loaded!");
}

function callGetChickenURL(chickenID) {
  var fullUrl = getChickenURL + chickenID;

  var request = $.get(fullUrl, function(data) {
    addCustomChicken(data);
  });

  request.fail(function() {
    console.error("Error fetching chicken data: " + chickenID);
  });
}

function addCustomChicken(chickenDNA) {
  var newChickenID = "chicken" + chickenCount++,
      newBlock = $(chickenBlock).attr('id', newChickenID);

  newBlock.appendTo(".chickenRow");

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
}