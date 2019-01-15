# nature - A simple genetic algorithms library for Clojure
<a href="https://icons8.com/icon/20873/organic-food"><img src="resources/icons8-nature.png"></a>
[![Clojars Project](https://img.shields.io/clojars/v/nature.svg)](https://clojars.org/nature)
[![Dependencies Status](https://versions.deps.co/nnichols/nature/status.svg)](https://versions.deps.co/nnichols/nature)

> Don't you see the danger, John, inherent in what you're doing here?
> Genetic power is the most awesome force the planet's ever seen, but you wield it like a kid that's found his dad's gun.
> - Dr. Ian Malcolm from [*Jurassic Park*](https://www.imdb.com/title/tt0107290/)

## Installation

A deployed copy of the most recent version of [nature can be found on clojars.](https://clojars.org/nature)
To use it, add the following as a dependency in your project.clj file:

```
[nature "0.0.1"]
```

The next time you build your application, [Leiningen](https://leiningen.org/) should pull it automatically.
Alternatively, you may clone or fork the repository to work with it directly.

## Usage

To see how the application works, try executing it:
```bash
~/nature (master)
$ lein run
```

After a while, you should see something like the following response:
```clojure
({:genetic-sequence [1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1],
  :guid 61369be9-7039-4abe-adea-2471b6471ee8,
  :parents [33f23983-c105-409c-999e-41c5ca14e6ae 569dc99e-b7d1-4a47-89c9-7cf88b52f114],
  :age 0,
  :fitness-score 25})
```

Fantastic!
Now, what does all of this mean?

* **genetic-sequence** - A collection of data elements that represents a solution candidate to the problem modeled by your fitness function.
* **guid** - A string representing a [v1 UUID](https://en.wikipedia.org/wiki/Universally_unique_identifier) uniquely identifying this solution candidate.
* **parents** - A collection of one or more guids representing the individuals used to create the individual. If the individual is created during the initialization phase, it is assigned to a vector containing the string ["Initializer"](https://github.com/nnichols/nature/blob/master/src/nature/population_presets.clj) instead.
* **age** - The number of generations an individual has been a member of.
* **fitness-score** - A number representing how well the **genetic-sequence** solves the problem at hand.

These five pieces of information are how nature models an individual.
In this case, the individual we see above is the best solution nature found to our given problem after executing all of the generations we specified.
Now, how do we solve problems of our own?

The core of nature's functionality lies within the `evolve` function.
```clojure
(evolve allele-set genome-length population-size generations fitness-function binary-operators unary-operators)
```

* `allele-set` is a collection of legal genome values.
* `genome-length` is the enforced size of each genetic sequence
* `population-size` is the enforced number of individuals that will be created
* `generations` is the number of iterations the algorithm will cycle through
* `fitness-function` is a partial function accepting generated sequences to evaluate solution qualities
* `binary-operators` is a collection of partial functions accepting and returning 1 or more individuals
* `unary-operators` is a collection of partial functions accepting and returning exactly 1 individual
* `options` an optional map of pre-specified keywords to values that further tune the behavior of nature.

Additionally, and 8th argument may be supplied: `options` is a map that currently checks for two keys:
* `:carry-over` an integer representing the top n individuals to be carried over between each generation. Default is 1.
* `:solutions` an integer representing the top n individuals to return after evolution completes. Default is 1.

So, how do we go from this function to the solution above?
Let's look at a simple example.
Say we commonly work with lists of twenty five binary digits:
```
(1 0 0 0 0 0 1 0 1 0 0 0 0 1 0 1 1 0 0 0 0 0 0 0 0)
(0 0 0 0 0 1 0 0 1 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0)
(1 1 0 1 1 0 0 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 0 1)
...
```
As we see each list, we're asked to evaluate each list by counting up the number of times we see a 1 present.
```
(1 0 0 0 0 0 1 0 1 0 0 0 0 1 0 1 1 0 0 0 0 0 0 0 0) = 6
(0 0 0 0 0 1 0 0 1 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0) = 4
(1 1 0 1 1 0 0 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 0 1) = 20
...
```
Now we've been given the task to find the list or lists with the highest possible value we can identify before lunch.
Being selectively skilled at mathematics and basic reasoning, we know that 2^25 = 33,554,432 possible lists exist.
It's way too many to check by hand, a close answer is acceptable, and we can easily compare the relative qualities of solutions.
Given these factors, we decide Genetic Algorithms are perfect for the task, and we clone nature to write the following:

```clojure
(evolve pp/binary-genome
        pp/default-sequence-length
        pp/default-population-size
        pp/default-generation-count
        pp/sum-alleles
        [(go/crossover pp/sum-alleles)]
        [(partial go/mutation-operator pp/sum-alleles pp/binary-genome 1)]
        {:solutions 1, :carry-over 5})
```
What luck!
The repository contains code to solve this very problem out of the box!
Now, to document why these settings can help solve each problem, we'll step through each one.

* `pp/binary-genome` is the vector `[0 1]`. Our lists may only contain those two values, so we'll tell nature to build individuals with that domain.
* `pp/default-sequence-length` is the integer 25. Since we're examining lists on 25 elements, our individuals need to match that restriction.
* `pp/default-population-size` is the integer 50. Any positive integer could be used, but there are tradeoffs. Smaller values will execute quicker, and large values will perform a wider search.
* `pp/default-generation-count` is also the integer 50. Any positive integer could be used, but there are tradeoffs just like the population size.
* `pp/sum-alleles` is `(partial apply +)`. We want to add each value in our sequence together, since that is the same as counting the occurrences of 1.
* `[(go/crossover pp/sum-alleles)]` is the binary genetic operator we want to use, including the fitness function it will use to evaluate the solutions it creates. We could have exchanged this with fitness-based scanning, or used both. The choice of operator can make a difference, but we're in a rush so we'll pick arbitrarily.
* `[(partial go/mutation-operator pp/sum-alleles pp/binary-genome 1)]` is the unary genetic operator we want to use, including the fitness function it will use to evaluate the solutions it creates, the set of alleles we can mutate to, and a probability out of 100 that we'll actually make any changes. This, like the binary genetic operator(s), can be tuned based on preference and the problem at hand.
* `{:solutions 1, :carry-over 5}` are the options we've selected. We only want the best solution we find, so we'll pick the top 1. We're also afraid that with such a large search space, we might loose good solutions between generations. We'll decide to keep the top 5 solutions between each round. This are parameters that can be tuned based on use case, but, for now, a few quick picks are fine.

From there, nature will run its course. 50 individuals with random genetic sequences containing a combination of 25 1s and 0s will be created. From there, they'll under go 50 cycles of reproduction and mutation, making sure to preserve the top 5 individuals. Once we've completed, we'll return the cream-of-the crop, and take off for lunch.

## Documentation Site

For more information on nature, please visit the full-length documentation [here.](https://nnichols.github.io/code/nature/intro)

## Automated Build And Repository Information

API documentation via [Codox.](https://nnichols.github.io/nature/api/index.html)

Code Coverage reports via [Cloverage.](https://nnichols.github.io/nature/coverage/index.html)

## Licensing

Copyright © 2018-2019 [Nick Nichols](https://nnichols.github.io/)

Distributed under the [Eclipse Public License Version 1.0](https://www.eclipse.org/legal/epl-v10.html)

[Organic Food Icon by Icons8](https://icons8.com/icon/20873/organic-food)
