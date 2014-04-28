# ItemToChat

![Preview](http://puu.sh/7m9ii.png)

## Summary

Ever thought it would be neat to have a way to display items in the chat like what you can see
in big MMORPG like World of Warcraft or Guild Wars 2? It's now possible with this plugin.

You can just middle click an item and it will send it to the chat.

## Commands

- ``/itc reload`` : Guess what it does :-)
- ``/itc send [player]`` : Show an item in the chat
- ``/itc send [player]`` : Sends an item to a player trough the chat
(when he click it, the item is taken from you inventory is put in his)
- ``/itc give [player]`` : Same than send but the item is not taken from your inventory
- ``/itc transaction <clear> [player]`` : Clear all transactions (from a player if specified)

## Permissions

- ``itemtochat.*``               : Give access to all ItemToChat features
- ``itemtochat.commands.*``      : Give access to all ItemToChat commands
- ``itemtochat.commands.reload`` : Reload config from file (default is op)
- ``itemtochat.commands.show``   : Use /itc show command (default is true)
- ``itemtochat.commands.send``   : Use /itc send command (default is true)
- ``itemtochat.commands.give``   : Use /itc give command (default is op)
- ``itemtochat.chat.*``          : Give access to the chat related features
- ``itemtochat.chat.message``    : Show an item into the chat using a placeholder (default is true)
- ``itemtochat.chat.click``      : Click to get item on usable messages (default is true)

## Config

*Insert some config related info here*

## Features

- [X] Display item in the chat by clicking it
- [X] Display item in the chat by typing ``[item]`` in the chat
- [ ] Display item in the chat with the possibility to click it to get it
- [ ] Possibility for admins to clear the transactions
- [ ] Handle all item data
- [ ] Add more stuff to the config

## Continous development

See the [jenkis server](http://ci.ribesg.fr/view/bendem/) to get the latest builds
(note that these builds can break your game, your maps and your house... Use them with precautions).
