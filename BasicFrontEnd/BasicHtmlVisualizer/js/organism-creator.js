"use strict";

var chromosomesHTML = "<div class='generatedChromosome'>Name: <input type='text' placeholder='Chromosome Name' name='chromosomeID' required><br>Traits:<div class='traits'></div><input type='button' value='+ Trait' onclick='addTraitClicked(this)'><br></div><br>",
    traitHTML = "<div class='trait'>Name: <input type='text' placeholder='Trait Name' name='traitId' required><br>GeneA Expression Value: <input type='number' placeholder='0 - 512' name='geneAEV' required><br>GeneA Combination Value: <input type='number' placeholder='0 - 1' name='geneACV'><br>GeneB Expression Value: <input type='number' placeholder='0 - 512' name='geneBEV' required><br>GeneB Combination Value: <input type='number' placeholder='0 - 1' name='geneBCV'><br></div><br>",
    chromosomeCount = 0,
    traitCount = 0;

function addChromosomeClicked() {
  var generatedChromosomeHTML = "C-" + chromosomeCount + ":" + chromosomesHTML;
  $("#chromosomes").append(generatedChromosomeHTML);
  chromosomeCount++;
}

function addTraitClicked(objButton) {
  var traitsDiv = $(objButton.parentElement).find(".traits"),
  generatedTraitHtml = "T-" + traitCount + ":" + traitHTML;
  $(traitsDiv).append(generatedTraitHtml);
  traitCount++;
}

function generateJSON() {
  console.log("YOU DID IT! I'm going to loop through the form elements now...");

  var formInputs = document.forms["organismForm"].getElementsByTagName("input");

  console.log(formInputs);

  var type = "",
      name = "",
      chromosomes = [],
      currentChromosome = null,
      chromosomeTraits = [],
      currentTrait;

  Array.from(formInputs).forEach(function(input) {
    switch (input.name) {
      case "organismType":
        type = input.value;
        break;
      case "organismName":
        name = input.value;
        break;
      case "chromosomeID":
        if(currentChromosome != null) {
          chromosomeTraits.push(currentTrait);
          currentChromosome.traits = chromosomeTraits;
          chromosomes.push(currentChromosome);
          chromosomeTraits = [];
        }
        currentChromosome = {};
        currentChromosome.chromosomeID = input.value;
        break;
      case "traitId":
        if(currentTrait != null) {
          chromosomeTraits.push(currentTrait);
        }
        currentTrait = {};
        currentTrait.traitId = input.value;
        break;
      case "geneAEV":
        currentTrait.geneA = {};
        currentTrait.geneA.expressionValue = input.value;
        break;
      case "geneACV":
        currentTrait.geneA.combinationValue = input.value;
        break;
      case "geneBEV":
        currentTrait.geneB = {};
        currentTrait.geneB.expressionValue = input.value;
        break;
      case "geneBCV":
        currentTrait.geneB.combinationValue = input.value;
        break;
      default:
        console.log("Don't know what this is: " + input.name);
        break;
    }
  });

  chromosomeTraits.push(currentTrait);
  currentChromosome.traits = chromosomeTraits;
  chromosomes.push(currentChromosome);

  var newOrganism = {};

  newOrganism.type = type;
  newOrganism.metadata = {};
  newOrganism.metadata.name = name;
  newOrganism.dna = {};
  newOrganism.dna.chromosomes = chromosomes;

  console.log(newOrganism);

  $("#formOutput").html(JSON.stringify(newOrganism, null, 2));

  return false;
}