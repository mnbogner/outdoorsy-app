# outdoorsy-app
Android code challenge for Outdoorsy

The UI was designed based on the provided images. 
The app sends search requests and displays name and image results in a RecyclerView.
MVVM architecture and LiveData was used for the app.
Retrofit2 library was used for network calls.
Gson library was used for parsing results.
Picasso library was used for loading images.

Known issues:
 - EditText used for search should be replaced with a search widget in the app bar
 - Image loading is slow and should be optimized
 - Should build up a hierarchy of objects so a GsonConverter could handle the results
 - Paging for search results still needs to be implemented
 - Improved error checking
 - Unit tests     

All Rights Reserved

Copyright (c) 2020 Matthew Bogner
