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
  breedGenepool();
}

function cullGenepoolButtonClicked() {
  console.log("This should start the culling process!");
}

function disableBreedButton() {
  console.log("Disabling breed button...");
  disableElementById("#breedButton");
}

function disableCullButton() {
  disableElementById("#cullButton");
}

function enableBreedButton() {
  enableElementById("#breedButton");
}

function enableCullButton() {
  enableElementById("#cullButton");
}

function disableElementById(elementId) {
  $(elementId).prop('disabled', true);
}

function enableElementById(elementId) {
  $(elementId).prop('disabled', false);
}