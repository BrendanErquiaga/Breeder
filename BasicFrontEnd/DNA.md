# Rough Summary of actual DNA

### DNA Structure
Every organism has X Chromosomes Pairs
Every Chromosome Pair contains 1 Chromosome Half from each parent
Every Chromosome Half contains Y Alleles
Each Allele is a sequence of genes that makes up a trait

Trait Expression is... complicated, Punnet Squares being the simplest explanation

### DNA Combination

For Each Parent
  For Each Chromosome Pair
    Pick 1 Chromosome Half
  Collect all Chromosomes Halves into 1/2 a set
Combine both sets of Chromosome Halves to create X Chromosome Pairs

# Proposed System

### DNA Structure
Every organism has X Chromosomes
Every Chromosome has Y Traits (synonymous with Allele in function)
Each Trait has Two Genes, one from each parent
Each Gene has a Expression Value(0-N) and a Combination Value(0-1)

Trait Expression is determined thusly: (KEY: CV=Combination Value, EV=Expression Value)

Scenario A (Heterozygous - Simple):
  GeneA-CV: 1 + GeneB-CV: 0
  AND
  GeneA-EV: X + GeneB-EV: Y
  = GeneA-EV: X will be expressed

Scenario B (Homozygous - Simple):
  GeneA-CV: 0.5 + GeneB-CV: 0.5
  AND
  GeneA-EV: X + GeneB-EV: X
  = EV X will be expressed

Scenario C (Heterozygous - Complicated):
  GeneA-CV: 0.6 + GeneB-CV: 0.4
  AND
  GeneA-EV: X + GeneB-EV: Y
  = X & Y will be 'averaged' together, weighted with their CV

Scenario D (Homozygous - Complicated):
  GeneA-CV: 0.5 + GeneB-CV: 0.5
  And
  GeneA-EV: X + GeneB-EV: Y
  = X & Y will be averaged together, no weighting

### DNA Combination

For Each Parent
  For Each Chromosome
    For Each Trait
      Pick one Gene at random
    Combine Chromosome Traits into a Chromosome Half
  Collect all Chromosomes Halves into 1/2 of a DNA object
Combine both 1/2s to make a new DNA object


