taxilocator
===========

An app I developed during my last year in college, which locates the closest taxi within a one mile radius 

How it works
---------------------

The taxi app periodically gets its location via GPS or through the phones network connection if GPS is not enabled. Once the co-oridantes are obtained the app sends it co-ordinates to the rest api which 
stores the location and information about the driver. 

The user app then dislays the cloest taxis to the users location within a one mile radius, the locations are displayed on the map and locations are queried periodically 
