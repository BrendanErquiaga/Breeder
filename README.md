# Hazi
Hazi (Grow) is an App where you can Create, Breed, Mutate, & Kill organisms. The organisms have lifecycles and pseudo-realistic DNA.

Hazi is comprised of several data driven microservices, all accessed through an API layer.

Eventually there will be a small visual microservice for ease of use.

## Microservices

*Base Services:*

1. Organism Service
2. Breeder Service
3. Mutation Service
4. Life Cycle Service

*Stretch Services:*

1. Visual Service
2. Exposed Connection for other Team Brendar Apps

*Scope Creep:*

1. User Service
2. Authentication Service
3. Queues & Event Abstraction

## Technologies

Most of the microservices use this simple serverless "stack":

1. API Gateway Layer
2. AWS Lambdas
3. AWS Step Functions
4. S3 Storage (JSON files)