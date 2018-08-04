function getChromosome(chromosomes, id) {
  for (var i = 0; i < chromosomes.length; i++) {
    if(chromosomes[i].chromosomeID === id) {
      return chromosomes[i];
    }
  }
  return undefined;
}

function getTraitExpressionValue(traits, id) {
  var trait = getTrait(traits,id);

  if(trait) {
    return getExpressionValue(trait.geneA, trait.geneB);
  }

  return undefined;
}

function getExpressionValue(geneA, geneB) {
  var geneA_expr = geneA.expressionValue,
      geneA_CV = geneA.combinationValue,
      geneB_expr = geneB.expressionValue,
      geneB_CV = geneB.combinationValue;

  if(geneA_expr == geneB_expr) {//If the Expression is the same, don't mess with it
    return geneA_expr;
  }

  if(geneA_CV === 1 && geneB_CV === 0) {//Dominant GeneA
    return geneA_expr;
  } else if(geneB_CV === 1 && geneA_CV === 0) {//Dominant GeneB
    return geneB_expr;
  } else if(geneA_CV == geneB_CV) {
    return (geneA_expr + geneB_expr) / 2;//Average of Genes
  } else {
    return getWeightedGeneAverage(geneA, geneB);//Weighted Average of Genes
  }
}

function getWeightedGeneAverage(geneA, geneB) {
  var geneA_expr = geneA.expressionValue,
      geneA_CV = 10 * geneA.combinationValue,
      geneB_expr = geneB.expressionValue,
      geneB_CV = 10 * geneB.combinationValue;

  var weightedA = geneA_expr * geneA_CV,
      weightedB = geneB_expr * geneB_CV,
      combined = weightedA + weightedB,
      combinedAverage = combined / (geneA_CV + geneB_CV);

  console.log('A: ' + weightedA + ' B: ' + weightedB + ' = ' + combined + ' avg: ' + combinedAverage);

  return combinedAverage;
}

function getTrait(traits, id) {
  for (var i = 0; i < traits.length; i++) {
    if(traits[i].traitId === id) {
      return traits[i];
    }
  }
  return undefined;
}