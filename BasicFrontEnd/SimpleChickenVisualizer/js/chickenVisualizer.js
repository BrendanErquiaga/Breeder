"use strict";

var chickenBlock = '<div class="chickenContainer" id="customChicken1"><div class="chicken"><div class="wing"></div><div class="eye"></div><div class="beak_top"></div><div class="crest crest1"></div><div class="crest crest2"></div><div class="crest crest3"></div><div class="wattle"></div></div><p>ID: <span class="chickenId"></span><br>Name: <span class="chickenName"></span></p></div>"',
  chickenCount = 0,
  getChickenURL = "https://du1ejfbfoe.execute-api.us-west-2.amazonaws.com/DEV/organism/";

$(document).ready(function() {
    pageLoad();
});

function pageLoad() {
  addCustomChicken(tropicalChickenDNA);
  addCustomChicken(burntChickenDNA);
  addCustomChicken(leghornChickenDNA);
  addCustomChicken(plymouthChickenDNA);
  callGetChickenURL(0);
}

function callGetChickenURL(chickenID) {
  var fullUrl = getChickenURL + chickenID;

  $.ajax({
        url: getChickenURL,
        type: 'GET',
        dataType: 'application/json',
        success: function(data){
            console.log("You can do anything!");
            console.log(JSON.stringify(data));
        }
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