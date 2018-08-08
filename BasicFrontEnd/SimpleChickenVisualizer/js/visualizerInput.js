"use strict";

var selectedChickens = [];

function chickenContainerSelected(chickenContainer) {
  $(chickenContainer).toggleClass("selected");
  chickenToggled($(chickenContainer).attr('id'));
}

function chickenToggled(chickenId) {
  if(selectedChickens.includes(chickenId)) {
    selectedChickens.splice( $.inArray(chickenId, selectedChickens), 1);
  } else {
    selectedChickens.push(chickenId);
  }
  console.log(selectedChickens);
}

function breedGenepoolButtonClicked() {
  console.log("This should start the breeding process!");
}

function cullGenepoolButtonClicked() {
  console.log("This should start the culling process!");
}