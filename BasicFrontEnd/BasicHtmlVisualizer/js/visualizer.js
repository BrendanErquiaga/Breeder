"use strict";

var organism;

$(document).ready(function() {
    pageLoad();
    input();
});

function pageLoad() {}

function input() {
  $('#load-data').on('click', function () {
    var input = $('#organism-input-data').val();
    $('#organism-input-data').val('');
		organism = JSON.parse(input);
    visualizeOrganism();
  })
}

function displayOrganismStats(statsChromosome) {
  var dataArea = $('#stats-data'),
      foodDesire = getTraitExpressionValue(statsChromosome.traits, 'foodDesire'),
      sleepDesire = getTraitExpressionValue(statsChromosome.traits, 'sleepDesire');

  dataArea.html('');
  dataArea.append('<p>Sleep Desire: ' + foodDesire + '</p>');
  dataArea.append('<p>Food Desire: ' + sleepDesire + '</p>');
}

function displayOrganismData() {
  var dataArea = $('#organism-data');
  dataArea.html('');
  dataArea.append('<p>Name: ' + organism.metadata.name + '</p>');
  dataArea.append('<p>Type: ' + organism.type + '</p>');
}

function visualizeOrganism() {
  displayOrganismData();
  displayOrganismVisuals();
}

function displayOrganismVisuals()
{
  var dna = organism.dna,
    appearanceChromosome = getChromosome(dna.chromosomes, 'appearance'),
    statsChromosome = getChromosome(dna.chromosomes, 'stats');

  setOrganismSize(appearanceChromosome);
  setOrganismColor(appearanceChromosome);
  displayOrganismStats(statsChromosome);
}

function setOrganismSize(appearanceChromosome) {
  var visualizationArea = $('#organism-visuals'),
      organismSize = getTraitExpressionValue(appearanceChromosome.traits, 'size');

  visualizationArea.css('width', organismSize);
  visualizationArea.css('height', organismSize);
}

function setOrganismColor(appearanceChromosome) {
  var visualizationArea = $('#organism-visuals'),
      organismColor = getTraitExpressionValue(appearanceChromosome.traits, 'color'),
      colorName = "";

  console.log('ColorValue: ' + organismColor);

  switch (organismColor) {
    case 0:
      colorName = "yellow";
      break;
    case 1:
      colorName = "blue";
      break;
    case 2:
      colorName = "green";
      break;
    case 3:
      colorName = "orange";
      break;
    default:
      colorName = "white";
      break;
  }

  visualizationArea.css('background-color', colorName);
}




