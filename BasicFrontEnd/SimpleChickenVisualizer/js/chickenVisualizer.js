"use strict";

var tropicalChicken = {
  "id" : "1",
  "name" : "tropical",
  "FeatherColor1" : "#A8E6CE",
  "FeatherColor2" : "#DCEDC2",
  "BeakColor" : "#FFD3B5",
  "WattleColor" : "#FF8C94",
  "FeatherPattern" : "1",
  "Size" : 10,
  "Life Span" : 13,
  "Sex" : 0
};

$(document).ready(function() {
    pageLoad();
});

function pageLoad() {
  loadCustomChicken("customChicken1", getChickenObject(tropicalChickenDNA));
  loadCustomChicken("customChicken2", getChickenObject(burntChickenDNA));
  loadCustomChicken("customChicken3", getChickenObject(leghornChickenDNA));
  loadCustomChicken("customChicken4", getChickenObject(plymouthChickenDNA));
}

function loadCustomChicken(chickenElementId, chicken) {
  var chickenParent = $("#" + chickenElementId);

  //SetMainFeatherColor
  $(chickenParent).find(".chicken").css("background-color", chicken.FeatherColor1);
  $(chickenParent).find(".wing").css("background-color", chicken.FeatherColor2);
  $(chickenParent).find(".beak_top").css("background-color", chicken.BeakColor);
  $(chickenParent).find(".crest").css("background-color", chicken.WattleColor);
  $(chickenParent).find(".wattle").css("background-color", chicken.WattleColor);

  $(chickenParent).find(".chickenId").html(chicken.id);
  $(chickenParent).find(".chickenName").html(chicken.name);
}