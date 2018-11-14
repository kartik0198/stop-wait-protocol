# DCOMProject-StopAndWaitProtocol

In this mini project, I have implemented the Stop and Wait protocol.

1. Background
   "stop-n-wait" is the fundamental technique to provide reliable transfer under unreliable packet delivery system.
   
 In this method of flow control, the sender sends a single frame to receiver & waits for an acknowledgment.

• The next frame is sent by sender only when acknowledgment of previous frame is received.

• This process of sending a frame & waiting for an acknowledgment continues as long as the sender has data to send. 
• The main advantage of stop & wait protocols is its accuracy. Next frame is transmitted only when the first frame is acknowledged. So there is no chance of frame being lost.
• The main disadvantage of this method is that it is inefficient. It makes the transmission process slow.
