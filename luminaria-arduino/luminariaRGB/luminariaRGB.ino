
#include <SPI.h>
#include <Ethernet.h>

byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
EthernetServer server(80);

int redPin = 5;
int greenPin = 6;
int bluePin = 9;
char requestInfo[26];

int fromHexaCharToInt(char hexa) {
	if (hexa == 'a' || hexa == 'A') { return 10; }
	else if (hexa == 'b' || hexa == 'B') { return 11; }
	else if (hexa == 'c' || hexa == 'C') { return 12;	}
	else if (hexa == 'd' || hexa == 'D') { return 13;	}
	else if (hexa == 'e' || hexa == 'E') { return 14;	}
	else if (hexa == 'f' || hexa == 'F') { return 15;	}
	else { return atoi(&hexa); }
}

int adjustColor(int color) {
	int newColor = map(color, 0, 255, 0, 50);
	newColor = map(newColor, 0, 50, 0, 255);
	return newColor;
}

void setup(){
  pinMode(redPin, OUTPUT);
  pinMode(bluePin, OUTPUT);
  pinMode(greenPin, OUTPUT);
  Ethernet.begin(mac);
  server.begin();
  Serial.begin(9600);
  Serial.println(Ethernet.localIP());
}

void changeColor(char request[]) {
  int red = 255 - adjustColor(fromHexaCharToInt(request[9]) * 16 + fromHexaCharToInt(request[10]));
  int green = 255 - adjustColor(fromHexaCharToInt(request[11]) * 16 + fromHexaCharToInt(request[12]));
  int blue = 255 - adjustColor(fromHexaCharToInt(request[13]) * 16 + fromHexaCharToInt(request[14]));
  analogWrite(redPin, red);
  analogWrite(greenPin, green);
  analogWrite(bluePin, blue);
}

void loop(){
  EthernetClient client = server.available();
  if (client) {
    int i = 0;
    boolean currentLineIsBlank = true;
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();
        if (i < 18) {
          requestInfo[i] = c;
          i++;
        }
        if (c == '\n' && currentLineIsBlank) {
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println("Connection: close");
          client.println();
          Serial.println(requestInfo);
          if (strncmp("GET /LED-", requestInfo, 9) == 0) {
            changeColor(requestInfo);
          }
          break;
        }
        if (c == '\n') {
          currentLineIsBlank = true;
        }
        else if (c != '\r') {
          currentLineIsBlank = false;
        }
      }
    }
    delay(1);
    client.stop();
  }
}
