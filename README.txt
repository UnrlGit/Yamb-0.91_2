For multiplayer: start GameServer class in server package first, then start Main class (GameServer can also be started right before Start Game button is pressed)
Single player doesn't need GameServer
For testing purposes of multi player's game completion, it is possible to uncomment first line and comment second line on gameComplete method, in Controller class.
That will preview how game completion looks like but game will be played on. As it is currently, it will only calculate game completion once all fields are full.