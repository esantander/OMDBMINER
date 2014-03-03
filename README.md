OMDBMINER
=========

This command line tool is used to query the OMDB API (omdbapi.com)for the latest and most fresh information given a set of imdbIDs. 

The tool queries the API for JSON responses, including Rotten Tomato information, for a list of imdbIDs that should be placed one-per-line on a text file.

The tool outputs the JSON responses to a folder of your choosing.

The tool has a very limited and non-robust capacity to then read the JSON responses and convert all JSON responses
in your chosen directory to a single .csv file. Significant problems have been encountered in gracefully binding
all the possible responses that the OMDB API returns, and this is an ongoing area of improvement.

dependencies: Java 7, Jackson JSON libraries

Thus, the tool has two modes:

###Mine-Only Mode

java main param1 param2 J

where param1 is a full file path of a list of IMDBids, placed on per line on a text file. The third parameter is simply a J.

###Mine and Parse Mode

java main param1 param2 param3
where param1 is a full file path of a list of IMDBids
and param2 is a full file path of a directory to be used to dump JSON files
and param3 is a full file path where a csv will be generated


The current version is recommended only for mining JSON. 
