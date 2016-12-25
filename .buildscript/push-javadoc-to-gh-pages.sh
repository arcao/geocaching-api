#!/bin/bash

SLUG="arcao/geocaching-api"
JDK="oraclejdk8"
BRANCH="master"

set -e

if [ "$TRAVIS_REPO_SLUG" == "$SLUG" ] && [ "$TRAVIS_JDK_VERSION" == "$JDK" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "$BRANCH" ]; then
  rm -rf $HOME/gh-pages/
  rm -rf $HOME/site-latest/

  echo -e "Publishing site...\n"

  cp -R target/site $HOME/site-latest

  cd $HOME
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "travis-ci"
  git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/$SLUG gh-pages > /dev/null

  cd gh-pages
  git rm -rf ./
  cp -Rf $HOME/site-latest/* ./
  git add -f .
  git commit -m "Latest javadoc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
  git push -fq origin gh-pages > /dev/null

  echo -e "Published Javadoc to gh-pages.\n"  
fi

