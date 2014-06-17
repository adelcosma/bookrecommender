{
    "byISBN": [
      {
          "source": "dbpedia",
          "byProcessing": "function(){return by}",
          "output": [ "s_isbn", "s_title", "s_synopsis", "s_language", "s_genre", "s_country", "s_author" ], 
          "mapping":{
            "isbn": "function(){ return ISBN.parse(s_isbn.split(',')[0].split(' ')[1]).asIsbn10();}",
            "title": "function(){return s_title}",
            "synopsis": "function(){return s_synopsis}",
            "language": "function(){return s_language}",
            "genre": "function(){return s_genre}",
            "country": "function(){return s_country}",
            "author": "function(){return s_author}" 
          },
          "query": "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\nPREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\nPREFIX dbpprop: <http://dbpedia.org/property/>\nPREFIX ontology: <http://dbpedia.org/ontology/>\nselect ?s_isbn ?s_title ?s_synopsis ?s_language ?s_genre ?s_country ?s_author\nwhere {\n?book rdf:type ontology:Book.\n?book dbpprop:isbn ?s_isbn. FILTER regex(?s_isbn, \"{{BY0}}\"). FILTER( langMatches( lang( ?s_isbn ), \"en\" ) ).\n?book dbpprop:genre ?_genre. OPTIONAL{?_genre rdfs:label ?s_genre. FILTER( langMatches( lang( ?s_genre ), \"en\" ) )}.\nOPTIONAL{?book dbpprop:name ?s_title. FILTER( langMatches( lang( ?s_title ), \"en\" ) )}.\nOPTIONAL{?book ontology:abstract ?s_synopsis. FILTER( langMatches( lang( ?s_synopsis ), \"en\" ) )}.\n?book dbpprop:language ?_language. OPTIONAL{?_language  dbpprop:iso ?s_language. FILTER( langMatches( lang( ?s_language ), \"en\" ) )}.\n?book dbpprop:country ?_country. OPTIONAL{?_country dbpprop:countryCode ?s_country. FILTER( langMatches( lang( ?s_country ), \"en\" ) )}.\n?book dbpprop:author ?_author. OPTIONAL{?_author dbpprop:name ?s_author. FILTER( langMatches( lang( ?s_author ), \"en\" ) )}.\n}",  
          "outputProcessing": "function(){for(var i=0;i<$output.length;i++){if($output[i].language.length == 2){return [$output[i]]}} return [$output[0]]}"
      }
    ],
    "byAuthor": [
      {
          "source": "dbpedia",
          "byProcessing": "function(){return by}",
          "output": [ "s_isbn", "s_title", "s_synopsis", "s_language", "s_genre", "s_country", "s_author" ], 
          "mapping":{
            "isbn": "function(){ return ISBN.parse(s_isbn.split(',')[0].split(' ')[1]).asIsbn10();}",
            "title": "function(){return s_title}",
            "synopsis": "function(){return s_synopsis}",
            "language": "function(){return s_language}",
            "genre": "function(){return s_genre}",
            "country": "function(){return s_country}",
            "author": "function(){return s_author}" 
          },
          "query": "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\nPREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\nPREFIX dbpprop: <http://dbpedia.org/property/>\nPREFIX ontology: <http://dbpedia.org/ontology/>\nselect ?s_isbn ?s_title ?s_synopsis ?s_language ?s_genre ?s_country ?s_author\nwhere {\n?book rdf:type ontology:Book.\n?book dbpprop:isbn ?s_isbn. FILTER( langMatches( lang( ?s_isbn ), \"en\" ) ).\n?book dbpprop:genre ?_genre. OPTIONAL{?_genre rdfs:label ?s_genre. FILTER( langMatches( lang( ?s_genre ), \"en\" ) )}.\n?book dbpprop:name ?s_title. FILTER( langMatches( lang( ?s_title ), \"en\" ) ).\nOPTIONAL{?book ontology:abstract ?s_synopsis. FILTER( langMatches( lang( ?s_synopsis ), \"en\" ) )}.\n?book dbpprop:language ?_language. OPTIONAL{?_language  dbpprop:iso ?s_language. FILTER( langMatches( lang( ?s_language ), \"en\" ) )}.\n?book dbpprop:country ?_country. OPTIONAL{?_country dbpprop:countryCode ?s_country. FILTER( langMatches( lang( ?s_country ), \"en\" ) )}.\n?book dbpprop:author ?_author. ?_author dbpprop:name ?s_author. FILTER regex( ?s_author, \"{{BY0}}\", \"i\"). FILTER( langMatches( lang( ?s_author ), \"en\" ) ).\n}",  
          "outputProcessing": "function(){var result = [];var selected = {};for(var i=0;i<$output.length;i++){if(selected[$output[i].isbn] === undefined){result.push($output[i]);selected[$output[i].isbn] = true;}}return $output}" 
      }
    ],
    "byGenre": [
      {
          "source": "dbpedia",
          "byProcessing": "function(){return by}",
          "output": [ "s_isbn", "s_title", "s_synopsis", "s_language", "s_genre", "s_country", "s_author" ], 
          "mapping":{
            "isbn": "function(){ return ISBN.parse(s_isbn.split(',')[0].split(' ')[1]).asIsbn10();}",
            "title": "function(){return s_title}",
            "synopsis": "function(){return s_synopsis}",
            "language": "function(){return s_language}",
            "genre": "function(){return s_genre}",
            "country": "function(){return s_country}",
            "author": "function(){return s_author}" 
          },
          "query": "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\nPREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\nPREFIX dbpprop: <http://dbpedia.org/property/>\nPREFIX ontology: <http://dbpedia.org/ontology/>\nselect ?s_isbn ?s_title ?s_synopsis ?s_language ?s_genre ?s_country ?s_author\nwhere {\n?book rdf:type ontology:Book.\n?book dbpprop:isbn ?s_isbn. FILTER( langMatches( lang( ?s_isbn ), \"en\" ) ).\n?book dbpprop:genre ?_genre. ?_genre rdfs:label ?s_genre. FILTER regex( ?s_genre, \"{{BY0}}\", \"i\").FILTER( langMatches( lang( ?s_genre ), \"en\" ) ).\n?book dbpprop:name ?s_title. FILTER( langMatches( lang( ?s_title ), \"en\" ) ).\nOPTIONAL{?book ontology:abstract ?s_synopsis. FILTER( langMatches( lang( ?s_synopsis ), \"en\" ) )}.\n?book dbpprop:language ?_language. OPTIONAL{?_language  dbpprop:iso ?s_language. FILTER( langMatches( lang( ?s_language ), \"en\" ) )}.\n?book dbpprop:country ?_country. OPTIONAL{?_country dbpprop:countryCode ?s_country. FILTER( langMatches( lang( ?s_country ), \"en\" ) )}.\n?book dbpprop:author ?_author. OPTIONAL{?_author dbpprop:name ?s_author. FILTER( langMatches( lang( ?s_author ), \"en\" ) ).}\n}",  
          "outputProcessing": "function(){var result = [];var selected = {};for(var i=0;i<$output.length;i++){if(selected[$output[i].isbn] === undefined){result.push($output[i]);selected[$output[i].isbn] = true;}}return $output}" 
      }
    ],
    "seriesNext": [
      {
          "source": "dbpedia",
          "byProcessing": "function(){return by}",
          "output": [ "s_isbn", "s_title", "s_synopsis", "s_language", "s_genre", "s_country", "s_author" ], 
          "mapping":{
            "isbn": "function(){ return ISBN.parse(s_isbn.split(',')[0].split(' ')[1]).asIsbn10();}",
            "title": "function(){return s_title}",
            "synopsis": "function(){return s_synopsis}",
            "language": "function(){return s_language}",
            "genre": "function(){return s_genre}",
            "country": "function(){return s_country}",
            "author": "function(){return s_author}" 
          },
          "query": "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\nPREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\nPREFIX dbpprop: <http://dbpedia.org/property/>\nPREFIX ontology: <http://dbpedia.org/ontology/>\nselect ?s_isbn ?s_title ?s_synopsis ?s_language ?s_genre ?s_country ?s_author\nwhere {\n ?nbook rdf:type ontology:Book.\n ?book rdf:type ontology:Book.\n ?nbook dbpprop:isbn ?ns_isbn. FILTER regex( ?ns_isbn, \"{{BY0}}\", \"i\"). FILTER( langMatches( lang( ?ns_isbn ), \"en\" ) ).\n ?nbook dbpprop:followedBy ?book.\n ?book dbpprop:isbn ?s_isbn. FILTER( langMatches( lang( ?s_isbn ), \"en\" ) ).\n ?book dbpprop:genre ?_genre. OPTIONAL{?_genre rdfs:label ?s_genre. FILTER( langMatches( lang( ?s_genre ), \"en\" ) )}.\n ?book dbpprop:name ?s_title. FILTER( langMatches( lang( ?s_title ), \"en\" ) ).\n OPTIONAL{?book ontology:abstract ?s_synopsis. FILTER( langMatches( lang( ?s_synopsis ), \"en\" ) )}.\n ?book dbpprop:language ?_language. OPTIONAL{?_language  dbpprop:iso ?s_language. FILTER( langMatches( lang( ?s_language ), \"en\" ) )}.\n ?book dbpprop:country ?_country. OPTIONAL{?_country dbpprop:countryCode ?s_country. FILTER( langMatches( lang( ?s_country ), \"en\" ) )}.\n ?book dbpprop:author ?_author. ?_author dbpprop:name ?s_author. FILTER( langMatches( lang( ?s_author ), \"en\" ) ).\n}",
          "outputProcessing": "function(){var result = [];var selected = {};for(var i=0;i<$output.length;i++){if(selected[$output[i].isbn] === undefined){result.push($output[i]);selected[$output[i].isbn] = true;}}return $output}" 
      }
    ]
}