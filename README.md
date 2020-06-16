# algobus - event driven shared memory trading bus

Based on http://mappedbus.io , algobus adds a framework to send trading messages between multiple Java processes, inspired by Island's "CORE" trading technology https://tradingplacesnewsletter.com/the-core-768495ebb033.

Latest Mappedbus benchmark processes 17,5 million messages (12 bytes) per second with an average latency of 56 ns on a Intel(R) Core(TM) i7-9700K CPU @ 3.60GHz (8 CPUs), ~3.6GHz

## Architecture

The framework contains a [BaseBot.java](algobus/src/bots/BaseBot.java) that deals with reading from and writing to the message bus. The BaseBot can be used to build any kind of component needed in the trading system, such as MarketData publisher, exchange gateways or strategy bots.
