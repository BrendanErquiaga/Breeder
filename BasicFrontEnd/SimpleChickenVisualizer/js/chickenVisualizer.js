"use strict";

var burntChicken = {
  "id" : "0",
  "name" : "burnt",
  "FeatherColor1" : "#581845",
  "FeatherColor2" : "#900C3F",
  "BeakColor" : "#FFC300",
  "WaddleColor" : "#FF5733",
  "FeatherPattern" : "1",
  "Size" : 10,
  "Life Span" : 13,
  "Sex" : 0
},
tropicalChicken = {
  "id" : "1",
  "name" : "tropical",
  "FeatherColor1" : "#A8E6CE",
  "FeatherColor2" : "#DCEDC2",
  "BeakColor" : "#FFD3B5",
  "WaddleColor" : "#FF8C94",
  "FeatherPattern" : "1",
  "Size" : 10,
  "Life Span" : 13,
  "Sex" : 0
},
coolChicken = {
  "id" : "2",
  "name" : "cool",
  "FeatherColor1" : "#9DE0AD",
  "FeatherColor2" : "#45ADAB",
  "BeakColor" : "#9BD1E5",
  "WaddleColor" : "#2A363B",
  "FeatherPattern" : "1",
  "Size" : 10,
  "Life Span" : 13,
  "Sex" : 0
};

$(document).ready(function() {
    pageLoad();
});

function pageLoad() {
  loadCustomChicken("customChicken1", tropicalChicken);
  loadCustomChicken("customChicken2", burntChicken);
  loadCustomChicken("customChicken3", coolChicken);
}

function loadCustomChicken(chickenElementId, chicken) {
  var chickenParent = $("#" + chickenElementId);

  //SetMainFeatherColor
  $(chickenParent).find(".chicken").css("background-color", chicken.FeatherColor1);
  $(chickenParent).find(".wing").css("background-color", chicken.FeatherColor2);
  $(chickenParent).find(".beak_top").css("background-color", chicken.BeakColor);
  $(chickenParent).find(".crest").css("background-color", chicken.WaddleColor);
  $(chickenParent).find(".wattle").css("background-color", chicken.WaddleColor);

  $(chickenParent).find(".chickenId").html(chicken.id);
  $(chickenParent).find(".chickenName").html(chicken.name);
}