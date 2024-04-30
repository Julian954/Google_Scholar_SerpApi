# Google_Scholar_SerpApi

### **Introducción:**
El objetivo de este repositorio es realizar una peticion a la api de serpapi para poder obtener datos de Google Scholar y guardarlos en la base de datos haciendo uso del patron de diseno MVC en Java.

La API de Google Scholar de SerpApi permite obtener los resultados de búsqueda de Google Scholar. Se accede a través del endpoint **`/search?engine=google_scholar`** y se puede consultar utilizando una solicitud GET. La API es útil para obtener información específica de Google Scholar.

Un usuario puede consultar lo siguiente: `https://serpapi.com/search?engine=google_scholar`utilizando una petición `GET.`

### **Parámetros de la API**:

*Consulta de búsqueda*

| Parámetro | Requerido | Función |
| --- | --- | --- |
| q | si | El parámetro define la consulta que desea buscar. También puede utilizar ayudas en su consulta como: author:o source:. |

*Parámetros avanzados de Google Scholar*

| Parámetro | Requerido | Función |
| --- | --- | --- |
| cites | Opcional | Parameter defines unique ID for an article to trigger Cited By searches. Usage of cites will bring up a list of citing documents in Google Scholar |
| as_ylo | Opcional | Parameter defines the year from which you want the results to be included. (e.g. if you set as_ylo parameter to the year 2018, the results before that year will be omitted.). |
| as_yhi | Opcional | Parameter defines the year until which you want the results to be included. (e.g. if you set as_yhi parameter to the year 2018, the results after that year will be omitted.). |
| scisbd | Opcional | Parameter defines articles added in the last year, sorted by date. It can be set to 1 to include only abstracts, or 2 to include everything. The default value is 0 which means that the articles are sorted by relevance. |
| cluster | Opcional | Parameter defines unique ID for an article to trigger All Versions searches. Example value: cluster=1275980731835430123. Usage of cluster together with q and cites parameters is prohibited. Use cluster parameter only. |

*Localización*

| Parámetro | Requerido | Función |
| --- | --- | --- |
| hl | Opcional | Parameter defines the language to use for the Google Scholar search. It's a two-letter language code. (e.g., en for English, es for Spanish, or fr for French). |
| lr | Opcional | Parameter defines one or multiple languages to limit the search to. It uses lang_{two-letter language code} to specify languages and | as a delimiter. (e.g., lang_fr|lang_de will only search French and German pages). |

*Paginación*

| Parámetro | Requerido | Función |
| --- | --- | --- |
| start | Opcional | Parameter defines the result offset. It skips the given number of results. It's used for pagination. (e.g., 0 (default) is the first page of results, 10 is the 2nd page of results, 20 is the 3rd page of results, etc.). |
| num | Opcional | Parameter defines the maximum number of results to return, limited to 20. (e.g., 10 (default) returns 10 results, 20 returns 20 results). |

*Tipo de Busqueda*

| Parámetro | Requerido | Función |
| --- | --- | --- |
| as_sdt | Opcional | Parameter can be used either as a search type or a filter.As a Filter (only works when searching articles):0 - exclude patents (default).7 - include patents.As a Search Type:4 - Select case law (US courts only). This will select all the State and Federal courts. e.g. as_sdt=4 - Selects case law (all courts) |

*Filtros Avanzados*

| Parámetro | Requerido | Función |
| --- | --- | --- |
| safe | Opcional | Parameter defines the level of filtering for adult content. It can be set to active or off, by default Google will blur explicit content. |
| filter | Opcional | Parameter defines if the filters for 'Similar Results' and 'Omitted Results' are on or off. It can be set to 1 (default) to enable these filters, or 0 to disable these filters. |
| as_vis | Opcional | Parameter defines whether you would like to include citations or not. It can be set to 1 to exclude these results, or 0 (default) to include them. |
| as_rr | Opcional | Parameter defines whether you would like to show only review articles or not (these articles consist of topic reviews, or discuss the works or authors you have searched for). It can be set to 1 to enable this filter, or 0 (default) to show all results. |

*Parametros SerpApi* 

| Parámetro | Requerido | Función |
| --- | --- | --- |
| engine | si | Set parameter to google_scholar to use the Google Scholar API engine. |
| no_cache | Opcional | Parameter will force SerpApi to fetch the Google Scholar results even if a cached version is already present. A cache is served only if the query and all parameters are exactly the same. Cache expires after 1h. Cached searches are free, and are not counted towards your searches per month. It can be set to false (default) to allow results from the cache, or true to disallow results from the cache. no_cache and async parameters should not be used together. |
| async | Opcional | Parameter defines the way you want to submit your search to SerpApi. It can be set to false (default) to open an HTTP connection and keep it open until you got your search results, or true to just submit your search to SerpApi and retrieve them later. In this case, you'll need to use our Searches Archive API to retrieve your results. async and no_cache parameters should not be used together. async should not be used on accounts with Ludicrous Speed enabled. |
| api_key | si | Parameter defines the SerpApi private key to use. |
| output | Opcional | Parameter defines the final output you want. It can be set to json (default) to get a structured JSON of the results, or html to get the raw html retrieved. |

### **Resultados API** :

| Resultado | Info |
| --- | --- |
| JSON Results | JSON output includes structured data for organic results. A search status is accessible through  search_metadata.status.  It flows this way:  Processing -> Success \\ Error.  If a search has failed, error will contain an error message.  search_metadata.id  is the search ID inside SerpApi. |
| HTML Results | HTML output is useful to debug JSON results or support features not supported yet by SerpApi. HTML output gives you the raw HTML results from Google. |

