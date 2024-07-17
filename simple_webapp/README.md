This Folder contains the code for a simple webapp, that will be used converted JSON file into tables and columns.
Here I used Node JS for the backend, PSQL for the Database. 
Following is the example JSON that can be used to converted into tables.

{
    "library": {
        "name": "Egmore Library",
        "foundingDate": "20-02-1885",
        "members": [
		{
			"memberID": 12,
			"person_name": "Ari",
			"person_age": "21"
		}
	],
        "books": [
	{
                "name": "Tirukural",
                "author": "Valluvar",
                "numberOfCopiesAvailable": "20",
                "takenBy": ["ref('member.12')"]
	},
	{
		"name": "Purananooru",
		"author": "none",
		"numberOfCopiesAvailable": "10",
		"takenBy":   []
	}
        ]
    }
}

