
        $(document).ready(function(){

        var elem = document.createElement('canvas');
        if(!(elem.getContext && elem.getContext('2d'))){ return };
          // Some simple loops to build up data arrays.
          var cosPoints = [];
          for (var i=0; i<2*Math.PI; i+=0.4){ 
            cosPoints.push([i, Math.cos(i)]); 
          }
            
          var sinPoints = []; 
          for (var i=0; i<2*Math.PI; i+=0.4){ 
             sinPoints.push([i, 2*Math.sin(i-.8)]); 
          }
            
          var powPoints1 = []; 
          for (var i=0; i<2*Math.PI; i+=0.4) { 
              powPoints1.push([i, 2.5 + Math.pow(i/4, 2)]); 
          }
            
          var powPoints2 = []; 
          for (var i=0; i<2*Math.PI; i+=0.4) { 
              powPoints2.push([i, -2.5 - Math.pow(i/4, 2)]); 
          } 
        });
