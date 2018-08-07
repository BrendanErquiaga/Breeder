"use strict";

var burntChickenDNA = {
  "id" : "3",
  "type": "chicken",
  "metadata": {
    "name": "Burnt Chicken"
  },
  "breedingRules" : {
    "restrictions" : "type"
  },
  "dna": {
    "chromosomes": [{
      "chromosomeID": "ChickenVisuals",
      "traits": [{
        "traitId": "FeatherColor1",
        "geneA": {
          "expressionValue": "5773381",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "5773381",
          "combinationValue": "1"
        }
      }, {
        "traitId": "FeatherColor2",
        "geneA": {
          "expressionValue": "9440319",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "0",
          "combinationValue": "0"
        }
      }, {
        "traitId": "FeatherPattern",
        "geneA": {
          "expressionValue": "3",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "3",
          "combinationValue": "1"
        }
      }, {
        "traitId": "BeakColor",
        "geneA": {
          "expressionValue": "FFC300",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "0",
          "combinationValue": "0"
        }
      }, {
        "traitId": "WattleColor",
        "geneA": {
          "expressionValue": "FF5733",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "FF5733",
          "combinationValue": "1"
        }
      }]
    }, {
      "chromosomeID": "Physical",
      "traits": [{
        "traitId": "Size",
        "geneA": {
          "expressionValue": "7",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "10",
          "combinationValue": "1"
        }
      }, {
        "traitId": "LifeSpan",
        "geneA": {
          "expressionValue": "10",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "9",
          "combinationValue": "1"
        }
      }, {
        "traitId": "Sex",
        "geneA": {
          "expressionValue": "1",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "1",
          "combinationValue": "1"
        }
      }]
    }]
  }
},
leghornChickenDNA = {
  "id": "2",
  "type": "chicken",
  "metadata": {
    "name": "Leghorn 1"
  },
  "breedingRules" : {
    "restrictions" : "type"
  },
  "dna": {
    "chromosomes": [{
      "chromosomeID": "ChickenVisuals",
      "traits": [{
        "traitId": "FeatherColor1",
        "geneA": {
          "expressionValue": "16577263",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "16577263",
          "combinationValue": "1"
        }
      }, {
        "traitId": "FeatherColor2",
        "geneA": {
          "expressionValue": "16577263",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "1929977",
          "combinationValue": "0"
        }
      }, {
        "traitId": "FeatherPattern",
        "geneA": {
          "expressionValue": "1",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "2",
          "combinationValue": "0"
        }
      }, {
        "traitId": "BeakColor",
        "geneA": {
          "expressionValue": "16381469",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "0",
          "combinationValue": "0"
        }
      }, {
        "traitId": "WattleColor",
        "geneA": {
          "expressionValue": "14038289",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "14038289",
          "combinationValue": "1"
        }
      }]
    }, {
      "chromosomeID": "Physical",
      "traits": [{
        "traitId": "Size",
        "geneA": {
          "expressionValue": "10",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "7",
          "combinationValue": "1"
        }
      }, {
        "traitId": "LifeSpan",
        "geneA": {
          "expressionValue": "15",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "13",
          "combinationValue": "1"
        }
      }, {
        "traitId": "Sex",
        "geneA": {
          "expressionValue": "0",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "0",
          "combinationValue": "1"
        }
      }]
    }]
  }
},
plymouthChickenDNA = {
  "id": "0",
  "type": "chicken",
  "metadata": {
    "name": "Plymouth Rock 1"
  },
  "breedingRules" : {
    "restrictions" : "type"
  },
  "dna": {
    "chromosomes": [{
      "chromosomeID": "ChickenVisuals",
      "traits": [{
        "traitId": "FeatherColor1",
        "geneA": {
          "expressionValue": "0",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "5263440",
          "combinationValue": "0"
        }
      }, {
        "traitId": "FeatherColor2",
        "geneA": {
          "expressionValue": "16777215",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "16777215",
          "combinationValue": "1"
        }
      }, {
        "traitId": "FeatherPattern",
        "geneA": {
          "expressionValue": "3",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "3",
          "combinationValue": "1"
        }
      }, {
        "traitId": "BeakColor",
        "geneA": {
          "expressionValue": "16758049",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "0",
          "combinationValue": "0"
        }
      }, {
        "traitId": "WattleColor",
        "geneA": {
          "expressionValue": "16720205",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "16720205",
          "combinationValue": "1"
        }
      }]
    }, {
      "chromosomeID": "Physical",
      "traits": [{
        "traitId": "Size",
        "geneA": {
          "expressionValue": "13",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "9",
          "combinationValue": "1"
        }
      }, {
        "traitId": "LifeSpan",
        "geneA": {
          "expressionValue": "10",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "9",
          "combinationValue": "1"
        }
      }, {
        "traitId": "Sex",
        "geneA": {
          "expressionValue": "1",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "1",
          "combinationValue": "1"
        }
      }]
    }]
  }
},
tropicalChickenDNA = {
  "id": "4",
  "type": "chicken",
  "metadata": {
    "name": "Tropical"
  },
  "breedingRules" : {
    "restrictions" : "type"
  },
  "dna": {
    "chromosomes": [{
      "chromosomeID": "ChickenVisuals",
      "traits": [{
        "traitId": "FeatherColor1",
        "geneA": {
          "expressionValue": "11069134",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "11039950",
          "combinationValue": "0"
        }
      }, {
        "traitId": "FeatherColor2",
        "geneA": {
          "expressionValue": "14478786",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "14478847",
          "combinationValue": "0"
        }
      }, {
        "traitId": "FeatherPattern",
        "geneA": {
          "expressionValue": "1",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "2",
          "combinationValue": "0"
        }
      }, {
        "traitId": "BeakColor",
        "geneA": {
          "expressionValue": "16765877",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "16765877",
          "combinationValue": "0.5"
        }
      }, {
        "traitId": "WattleColor",
        "geneA": {
          "expressionValue": "16747668",
          "combinationValue": "0.5"
        },
        "geneB": {
          "expressionValue": "16747668",
          "combinationValue": "0.5"
        }
      }]
    }, {
      "chromosomeID": "Physical",
      "traits": [{
        "traitId": "Size",
        "geneA": {
          "expressionValue": "6",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "8",
          "combinationValue": "1"
        }
      }, {
        "traitId": "LifeSpan",
        "geneA": {
          "expressionValue": "17",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "18",
          "combinationValue": "1"
        }
      }, {
        "traitId": "Sex",
        "geneA": {
          "expressionValue": "0",
          "combinationValue": "1"
        },
        "geneB": {
          "expressionValue": "0",
          "combinationValue": "1"
        }
      }]
    }]
  }
};

function getChickenObject(chickenDNA) {
  var chicken = {},
      visualsChromosome = getChromosome(chickenDNA.dna.chromosomes, "ChickenVisuals"),
      physicalChromosome = getChromosome(chickenDNA.dna.chromosomes, "Physical");

  chicken.id = chickenDNA.id;
  chicken.name = chickenDNA.metadata.name;
  chicken.FeatherColor1 = getTraitExpressionValue(visualsChromosome.traits, "FeatherColor1");
  chicken.FeatherColor1 = getHexColorFromDNAColor(chicken.FeatherColor1);

  chicken.FeatherColor2 = getTraitExpressionValue(visualsChromosome.traits, "FeatherColor2");
  chicken.FeatherColor2 = getHexColorFromDNAColor(chicken.FeatherColor2);

  chicken.BeakColor = getTraitExpressionValue(visualsChromosome.traits, "BeakColor");
  chicken.BeakColor = getHexColorFromDNAColor(chicken.BeakColor);

  chicken.WattleColor = getTraitExpressionValue(visualsChromosome.traits, "WattleColor");
  chicken.WattleColor = getHexColorFromDNAColor(chicken.WattleColor);

  chicken.FeatherPattern = getTraitExpressionValue(visualsChromosome.traits, "FeatherPattern");

  chicken.Size = getTraitExpressionValue(physicalChromosome.traits, "Size");
  chicken.Sex = getTraitExpressionValue(physicalChromosome.traits, "Sex");
  //chicken.LifeSpan = getTraitExpressionValue(physicalChromosome.traits, "LifeSpan"); TODO Fix LifeSpan traits

  return chicken;
}

