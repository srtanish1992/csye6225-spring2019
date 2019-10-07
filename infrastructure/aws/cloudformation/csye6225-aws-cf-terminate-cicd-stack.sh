#!/bin/bash

BASEDIR=$(dirname "$0")
PARAM_FILE_PATH=$BASEDIR"/parameters.json"

circleci_pstack=$(jq -r '.[0].cicd_stack' "$PARAM_FILE_PATH")

echo "Circleci packer roles stack: ${circleci_pstack}"

read -p "Continue?(Y/n): " confirm && [[ $confirm == [yY] || $confirm == [yY][eE][sS] ]] || exit 1

echo "Deleting Stack: ${circleci_pstack}"
aws cloudformation delete-stack --stack-name ${circleci_pstack}

aws cloudformation wait stack-delete-complete --stack-name ${circleci_pstack}
echo "Stack successfully deleted...!"
