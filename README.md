# algobus - event driven shared memory trading bus

Based on http://mappedbus.io , algobus adds a framework to send trading messages between multiple Java processes, inspired by Island's "CORE" trading technology https://tradingplacesnewsletter.com/the-core-768495ebb033.

Latest Mappedbus benchmark processes 17,5 million messages (12 bytes) per second with an average latency of 56 ns on a Intel(R) Core(TM) i7-9700K CPU @ 3.60GHz (8 CPUs), ~3.6GHz

