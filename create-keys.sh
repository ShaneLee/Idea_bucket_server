#!/usr/bin/env bash

# Create public and private keys

openssl genrsa -des3 -out idea-bucket.pem 2048
openssl rsa -in idea-bucket.pem -out idea-bucket.pem -outform PEM
openssl rsa -in idea-bucket.pem -outform PEM -pubout -out idea-bucket.pub.pem
