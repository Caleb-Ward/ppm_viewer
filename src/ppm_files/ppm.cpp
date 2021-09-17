#include <iostream>
#include <fstream>
#include <cmath>
using namespace std;
class pixel_class {
private:
 int red, green, blue;
public:
 void loaddata(int v1, int v2, int v3);
 void datatofile(fstream & ppmfile);
 int getR() { return red; }
 int getG() { return green; }
 int getB() { return blue; }
};
void IrelandFlag();
string filename;
pixel_class picture[500][250];
int main() {
 int x, y;
 fstream myfile;
 IrelandFlag();
 filename = "Flag.ppm";
 myfile.open(filename.c_str(), fstream::out);
 myfile << "P3\n";
 myfile << "# " << filename << endl;
 myfile << 500 << " " << 250 << endl;
 myfile << 256 << endl;
 for (y = 0; y < 250; y++) {
 for (x = 0; x < 500; x++) {
 picture[x][y].datatofile(myfile);
 }
 myfile << endl;
 }
 myfile.close();
}

void IrelandFlag() {
  int r = 125, g = 125, b = 125;
 int x, y;
 // green block up to one third of 500
 for (y = 0; y < 250; y++) {
   for (x = 0; x < 500; x++) {
    r = y;
    g = x/2;
    b = 125;
    //cout  << r << " ";
   picture[x][y].loaddata(r ,g,b);
   }
 }
 // int radius = 20;
 // for(int y=-radius; y<=radius; y++)
 //    for(int x=-radius; x<=radius; x++)
 //        if(x*x+y*y <= radius*radius)
 //            picture[(150+x)][(200+y)].loaddata(0,0,0);
 //  for(int y=-radius; y<=radius; y++)
 //     for(int x=-radius; x<=radius; x++)
 //         if(x*x+y*y <= radius*radius)
 //             picture[(400+x)][(200+y)].loaddata(0,0,0);

   // for (y = 150; y < 160; y++) {
   //   for (x = 200; x < 240; x++) {
   //
   //   picture[x][y].loaddata(128,0,0);
   //   }
   // }
   // for (y = 150; y < 160; y++) {
   //   for (x = 325; x < 335; x++) {
   //
   //   picture[x][y].loaddata(128,0,0);
   //   }
   // }

 // for (y = 90; y < 160; y++) {
 //   for (x = 0; x < 500; x++) {
 //   picture[x][y].loaddata(255,255,255);
 //   }
 // }
 // // white block from one third to two thirds of 500
 // for (y = 0; y < 250; y++) {
 //   for (x = 110; x < 140; x++) {
 //   picture[x][y].loaddata(0, 0, 150);
 //   }
 // }
 // for (y = 110; y < 140; y++) {
 //   for (x = 0; x < 500; x++) {
 //   picture[x][y].loaddata(0, 0, 150);
 //   }
 // }
}

//--------------- methods for the pixel_class ------------
void pixel_class::loaddata(int v1, int v2, int v3) {
 red = v1;
 green = v2;
 blue = v3;
}
void pixel_class::datatofile(fstream & ppmfile) {
 // write the data for one pixel to the ppm file
 ppmfile << red << " " << green;
 ppmfile << " " << blue << " ";
}
