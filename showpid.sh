#!/bin/bash
ps aux | grep ./rest/target/rest-1.0-jar-with-dependencies.jar | grep -v "grep" | awk {'print $2'}
