#!/bin/bash

SLUG="arcao/geocaching-api"
JDK="oraclejdk8"

set -e

if [ "$TRAVIS_REPO_SLUG" == "$SLUG" ] && [ "$TRAVIS_JDK_VERSION" == "$JDK" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_TAG" != "" ]; then
  rm -rf $HOME/gh-pages/
  rm -rf $HOME/site-latest/

  echo -e "Publishing website...\n"
  mvn clean site

  cp -R target/site $HOME/site-latest

  cd $HOME
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "travis-ci"
  git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/$SLUG gh-pages > /dev/null

  cd gh-pages
  git rm -rf ./
  cp -Rf $HOME/site-latest/* ./
  git add -f .
  git commit -m "Website for $TRAVIS_TAG"
  git push -fq origin gh-pages > /dev/null

  echo -e "Published website to gh-pages.\n"  
fi

