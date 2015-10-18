# calhacks2
<h1>Data Density Voronoi</h1>

This 'hack' allows a company to visualize which vendors their customers are using most through the use of voronoi diagrams.
Currently, this hacks draws data about users and merchants from the <b>CapitalOne Hackathon API</b> and uses the <b>HERE Geocoder API</b> to convert addresses into lat/long coordinates.

We made the each cell in the voronoi represent a vendor (Symbolized as a '$', a merchant in the CapOne API), and each point is a the position of a customer. We are able to visualize how dense a cell is by just looking at the clustering of the dots within each cell, as they are color-cooded to correspond with the color of the vendors.

<div style="text-align:center">
<img src='https://raw.githubusercontent.com/pikazu/calhacks2/master/voronoid.PNG'/>
<small>Currently we use a black background, but we hope to overlay it on a cartesian map to show that these points were drawn from real life locations</small>
</div>

<h4>Current Uses</h4>
Our program, generalized, will be able to give a company visual data on how their customers are using their real life locations. In our application, we would be able to tell you which ATMs would need to be resupplied as people often use the ATMs closest to them. This, along with their transaction data at each ATM, would provide valuable insight in preventing understocked ATMs and customer frustration.

<h4>Future Additions</h4>
With some more time, we hope to apply our hack to other uses, such as visualizing coffee shops and coffee drinkers to make supply shipments more efficent (since our data visualization would show which shops are most visited). Furthermore, we could consider more factors instead of distance in calculating other attributes of a ceterain shop. For example, we could consider highering more employees at a location with a lot of traffic compared to other locations.

<h4>Challenges</h4>
The most challenging part of the hack was creating the voronoi diagram to visualize how clustered each cell in the diagram was. Another challenge was parseing through large amounts of API data and sorting it into data that we are able to process. As relatively new 'hackers', these challenges took most of our time, and forced us to stick with a working, less complicated, version of our 'hack'.

<h4>Contributions</h4>
I(Kazu) studied and gathered all of the API data, and processed it into a format for my partner Nikhil Ghosh to read. I used python to gather and parse the data, while Nikhil used Java to draw the Voronoi diagram.
