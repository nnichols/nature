#!/usr/bin/env bash

## Correct formatting
lein cljfmt fix

echo " "
echo "Bikeshed Style Analysis"
lein bikeshed -v

echo " "
echo "Eastwood Linting"
lein eastwood

echo " "
echo "Eastwood Linting"
lein cloverage

echo " "
echo "Unit Tests & Coverage Reporting"
lein cloverage -o docs/coverage

echo " "
echo "API Reporting"
lein codox
