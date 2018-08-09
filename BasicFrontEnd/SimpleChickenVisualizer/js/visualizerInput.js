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
  cullGenepool();
}

function disableAllButtons() {
  disableElementById("#breedButton");
  disableElementById("#cullButton");
}

function enableAllButtons() {
  enableElementById("#breedButton");
  enableElementById("#cullButton");
}

function disableElementById(elementId) {
  $(elementId).prop('disabled', true);
}

function enableElementById(elementId) {
  $(elementId).prop('disabled', false);
}