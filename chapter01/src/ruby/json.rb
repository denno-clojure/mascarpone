#! /usr/bin/env jruby
require "rubygems"
require "json"

# de-serializing:
source_string = '{"sample": "Hello, world!"}'
puts JSON(source_string).inspect
# => {"sample"=>"Hello, world!"}

# serializing:
source_object = ["Just another Ruby Array", {"null value" => nil}]
puts JSON(source_object)
# => ["Just another Ruby Array",{"null value":null}]