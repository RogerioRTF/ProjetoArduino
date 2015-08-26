
#include <SPI.h>
#include <Ethernet.h>

// MAC address from Ethernet shield sticker under board
byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
IPAddress ip(192, 168, 0, 111); // IP address, may need to change depending on network
EthernetServer server(80);  // create a server at port 80

String HTTP_req;          // stores the HTTP request
boolean LED_status = 0;   // state of LED, off by default
int redPin = 5;
int greenPin = 6;
int bluePin = 9;
char requestInfo[26];

int fromHexaCharToInt(char hexa) {
	if (hexa == 'a' || hexa == 'A') {
		return 10;
	}
	else if (hexa == 'b' || hexa == 'B') {
		return 11;
	}
	else if (hexa == 'c' || hexa == 'C') {
		return 12;
	}
	else if (hexa == 'd' || hexa == 'D') {
		return 13;
	}
	else if (hexa == 'e' || hexa == 'E') {
		return 14;
	}
	else if (hexa == 'f' || hexa == 'F') {
		return 15;
	}
	else {
		return atoi(&hexa);
	}
}

int adjustColor(int color) {
	int newColor = map(color, 0, 255, 0, 50);
	newColor = map(newColor, 0, 50, 0, 255);
	return newColor;
}





void setup()
{
  pinMode(redPin, OUTPUT);
  pinMode(bluePin, OUTPUT);
  pinMode(greenPin, OUTPUT);
  Ethernet.begin(mac);  // initialize Ethernet device
  server.begin();           // start to listen for clients
  Serial.begin(9600);       // for diagnostics
  Serial.println(Ethernet.localIP());


}

void changeColor(char request[]) {
  int red = adjustColor(fromHexaCharToInt(request[9]) * 16 + fromHexaCharToInt(request[10]));
  int green = adjustColor(fromHexaCharToInt(request[11]) * 16 + fromHexaCharToInt(request[12]));
  int blue = adjustColor(fromHexaCharToInt(request[13]) * 16 + fromHexaCharToInt(request[14]));
  red = 255 - red;
  green = 255 - green;
  blue = 255 - blue;
  
  Serial.println(red);
  Serial.println(green);
  Serial.println(blue);
  
  analogWrite(redPin, red);
  analogWrite(greenPin, green);
  analogWrite(bluePin, blue);
}


void loop()
{
  EthernetClient client = server.available();  // try to get client

  if (client) {  // got client?
    int i = 0;
    boolean currentLineIsBlank = true;
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();
        if (i < 18) {
          requestInfo[i] = c;
          i++;
        }
        //HTTP_req += c;

        if (c == '\n' && currentLineIsBlank) {
          // send a standard http response header
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println("Connection: close");
          client.println();

          Serial.println(requestInfo);
          if (strncmp("GET /LED-", requestInfo, 9) == 0) {
            changeColor(requestInfo);
            Serial.println("LED ACENDE");
            delay(1000);
          }
          HTTP_req = "";    // finished with request, empty string
          break;
        }
        // every line of text received from the client ends with \r\n
        if (c == '\n') {
          // last character on line of received text
          // starting new line with next character read
          currentLineIsBlank = true;
        }
        else if (c != '\r') {
          // a text character was received from client
          currentLineIsBlank = false;
        }
      } // end if (client.available())
    } // end while (client.connected())
    delay(1);      // give the web browser time to receive the data
    client.stop(); // close the connection
  } // end if (client)
}
