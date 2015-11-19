module experiments::vis2::vega::VegaChart

import experiments::vis2::vega::Vega;
import experiments::vis2::vega::ParameterTypes;

import Prelude;

                
VEGA  _stackedBar = vega(
            viewport = [1800, 1800]
             ,
            axes= [
                axe(scale="x", \type="x"
                )          
                ,
                axe(scale = "y", \type ="y",
                properties =("labels":("fill":("value":"green")),
                "title":("dy":("value":-30))
                ))
                ]
            ,
            scales = 
                [scale(name="x", \type = "ordinal", 
                   domain = DOMAIN::ref(\data = "table", field = "data.x"),
                   range = RANGE::lit(key= "width")
                 ),
                 scale(name = "y", \type = "linear",
                   domain = DOMAIN::ref(\data="stats", field = "sum"),
                   range = RANGE::lit(key="height")
                   ,nice = true
                 ),
                 scale(name="color", \type = "ordinal",
                   range = RANGE::lit(key="category20")
                 )
                ]
               ,
              \data =[datum(name="table"),
                      datum(name="stats", 
                          transform= [
                              transform(keys=["data.x"],                       
                                \type = "facet")      
                              ,transform(\value="data.y",
                               \type = "stats")                  
                              ],                     
                          source= "table")
                     ,datum(name="goal", 
                          transform = [    
                              transform(field = "keyId",           
                                  \type = "formula", expr = 
                                "[].indexOf(d.data.c)")
                                ,transform(
                                      \type = "sort", by = "keyId") 
                               // ,transform(keys = ["data.c", "data.x"],
                                //    \type = "facet")           
                                // ,transform(\value="data.y",
                                 //    \type = "stats")
                                  // ,transform(field = "chk",\type = "formula", expr = 
                                  // "alert(JSON.stringify(d))"            
                                 // )
     
                                  // ,transform(\type = "flatten") 
                                  // ,transform(field = "chk",\type = "formula", expr = 
                                  // "alert(JSON.stringify(d))"            
   // "(function(e) {alert(JSON.stringify(e));})(d)" 
   //                               )
                                          
                               ] 
                               ,source= "table"               
                         )            
                          
                   ]
                   ,
             padding= padding(left=100, bottom = 30, top = 10, right = 100),
             marks = [mark(\type = "group",
                       marks=  [mark(\type="rect",
                                  properties = (
                                     "enter":(
                                       "fill":("scale":"color", "field":"data.c"),
                                       "width":("scale":"x", "offset":-1.0
                                                           , "band":true
                                       ),
                                      "x":("scale":"x","field":"data.x"),
                                      "y":("scale":"y","field":"y"),
                                      "y2":("scale":"y","field":"y2")
                                      ),
                                     "update":  ("fillOpacity":("value":1.0)),
                                     "hover":   ("fillOpacity":("value":0.5))                         
                                  )
                               
                                )
                             ]
                          ,
                        from = datum(\data="goal", 
                               transform = [             
                               // transform(field = "keyId",
                               //     \type = "formula", expr = "[].indexOf(d.data.x)") 
                               //,transform(
                               //       \type = "sort", by = "keyId") ,
                                 //transform(field = "chk",\type = "formula", expr = 
                                 //   "alert(JSON.stringify(d))"            
                                 // ) 
                               transform(keys = ["data.c"],
                                    \type = "facet")                 
                               ,transform(point = "data.x", 
                                    height = "data.y",
                                    \type = "stack") 
                         
                               ]                 
                         )                       
                   )
                   ],
                legends = []          
                ); 
                
 
 
 public VEGA() stackedBar(bool grid = false, 
    map[str, str] title = (), map[str, str] legends = (), list[str] palette =[],
    map[str, TICKLABELS] tickLabels =  ()
    , map[str, str] \format = (), map[str, int] ticks = (), map[str, list[str]] values = ()
    , list[str] groupOrder = [], num gap = 99999
    ) {
    return VEGA() {
        return update(_stackedBar, grid = grid, title = title, legends = legends,
        tickLabels = tickLabels,  palette = palette
        , \format = \format, ticks = ticks, values = values, groupOrder = groupOrder
        , offset = gap
        );
        };
    }
    
VEGA  _histogram = vega(
            viewport = [1800, 1800]
             ,
            axes= [
                axe(scale="x", \type="x", 
                  tickSize = 0, offset = -12
                )
               // , axe(scale = "y", \type ="y"              
               // )
                ]
           ,
           scales = 
                [scale(name="x", \type = "ordinal", 
                   domain = DOMAIN::ref(\data = "table", field = "data.x"),
                   range = RANGE::lit(key= "width"), padding = 0.2
                 ),
                  scale(name = "g", \type = "ordinal",
                   domain = DOMAIN::ref(\data="goal", field = "key"),
                   range = RANGE::lit(key="height"), padding = 0.15
                 )
                 ,scale(name="color", \type = "ordinal",
                   range = RANGE::lit(key="category10")
                  )
                ]        
            ,            
              \data =[datum(name="table"),
                  datum(name="goal", source="table", 
                  transform=[
                     //transform(field = "chk",\type = "formula", expr = 
                     //               "alert(JSON.stringify(d))"), 
                     transform(\type="facet", keys=["data.c"])
                  ])]
             ,
             padding= padding(left=100, bottom = 100, top = 50, right = 10),
             marks = [
                      mark(\type="text", from = datum(\data="goal")
                          ,
                          properties = (
                                  "enter":(
                                     "x": ("group": "width", "mult": 0.5),
                                     "y": ("scale": "g", "field": "key", "offset":-2),
                                     "fontWeight":("value":"bold"),
                                     "text":("field": "key"),
                                     "align":("value":"center"),
                                     "baseline":("value":"bottom"),
                                     "fill":("value":"black")                  
                                  )    
                          ))
                      ,mark(\type = "group",
                        from = datum(\data="goal"), 
                         scales =  [
                         scale(name="y", \type = "linear", 
                                   domain = DOMAIN::ref(\data = "table", field = "data.y"),
                                   range = RANGE::lit(key= "height"))          
                                   ],
                          axes = [ axe(\type = "y", scale = "y")]
                          ,properties = (
                                  "enter":(
                                     "x":("value":0.5)
                                      ,"y":("scale":"g", "field":"key")
                                      ,"height":("scale":"g", "band":true)
                                      ,"width":("group": "width")
                                      ,"stroke":("value": "#ccc")
                                      // ,"strokeWidth":("value": "10")
                                     )
                                   )   
                                     ,    
                         marks=[
                             
                             
                             mark(\type="rect",
                                  properties = (
                                     "enter":(      
                                        "fill":("scale":"color", "field":"data.c")
                                        , "x":("scale":"x", "field":"data.x")
                                        ,"width": ("scale":"x", "band":true)
                                        ,"y":("scale":"y", "field":"data.y")
                                        , "y2":("scale":"y","value":0)
                                       )
                                      ,
                                     "update":  ("fillOpacity":("value":1.0)),
                                     "hover":   ("fillOpacity":("value":0.5))
                                      )  
                  
                               
                                ) 
                       
          
                      ]                                    
                     )
                   ]         
                ); 
    
    
                
 public VEGA() histogram(bool grid = false, 
    map[str, str] title = (), map[str, str] legends = (), list[str] palette =[],
    map[str, TICKLABELS] tickLabels =  ()
    , map[str, str] \format = (), map[str, int] ticks = (), map[str, list[str]] values = ()
    , list[str] groupOrder = [], num gap = 99999
    ) {
    return VEGA() {
        return update(_histogram, grid = grid, title = title, legends = legends,
        tickLabels = tickLabels,  palette = palette
        , \format = \format, ticks = ticks, values = values, groupOrder = groupOrder
        , offset = gap
        );
        };
    }
    
               
 VEGA  _stackedArea = vega(
            viewport = [1800, 1800]
             ,
            axes= [
                   axe(scale="x", \type="x", grid = true)
                   ,
                   axe(scale = "y", \type ="y", grid = true
                  )
                ]
            ,
            scales = 
                [scale(name="x", \type = "linear", 
                   domain = DOMAIN::ref(\data = "table", field = "data.x"),
                   range = RANGE::lit(key= "width")
                 ),
                 scale(name = "y", \type = "linear",
                   domain = DOMAIN::ref(\data="stats", field = "sum"),
                   range = RANGE::lit(key="height")
                   ,nice = true
                 ),
                 scale(name="color", \type = "ordinal",
                   range = RANGE::lit(key="category10")
                 )
                ]
               ,
              \data =[datum(name="table"),
                      datum(name="stats", 
                          transform= [
                              transform(keys=["data.x"],                       
                                \type = "facet"),      
                              transform(\value="data.y",
                                \type = "stats")
                              ],                     
                          source= "table")
                   ]
                   ,
             padding= padding(left=100, bottom = 30, top = 10, right = 10),
             marks = [mark(\type = "group",
                         marks=[
                             mark(\type="area",
                                  properties = (
                                     "enter":(
                                      "fill":("scale":"color", "field":"data.c"),
                                      "interpolate":("value":"monotone"),
                                      "x":("scale":"x","field":"data.x"),
                                      "y":("scale":"y","field":"y"),
                                      "y2":("scale":"y","field":"y2")
                                      ),
                                     "update":  ("fillOpacity":("value":1.0)),
                                     "hover":   ("fillOpacity":("value":0.5))   
                                  )
                               
                                )
                             ]
                          ,
                        from = datum(\data="table",
                               transform = [
                                  transform(keys = ["data.c"],
                                    \type = "facet"),
                                  transform(point = "data.x", 
                                    height = "data.y",
                                    \type = "stack")
                               ] 
                         
                         )                       
                   )
                   ]            
                ); 
 
 public VEGA() stackedArea(bool grid = false, 
    map[str, str] title = (), map[str, str] legends = ()
    ,map[str, TICKLABELS] tickLabels = ()
    , list[str] palette = []
    , map[str, str] \format = (), map[str, int] ticks = (), map[str, list[str]] values = ()
    ) {
    return VEGA() {return update(_stackedArea, grid = grid, title = title, legends = legends,
        tickLabels = tickLabels,  palette = palette
        , \format = \format, ticks = ticks, values = values
        );};
    }
    
VEGA  _groupedBar = vega(
            viewport = [1800, 1800]
             ,
            axes= [
                axe(scale="x", \type="x", 
                  tickSize = 0, tickPadding = 8
                ),
                axe(scale = "y", \type ="y"              
                )
                ]
            ,
            scales = 
                [scale(name="x", \type = "ordinal", 
                   domain = DOMAIN::ref(\data = "table", field = "data.x"),
                   range = RANGE::lit(key= "width"), padding = 0.2
                 ),
                 scale(name = "y", \type = "linear",
                   domain = DOMAIN::ref(\data="table", field = "data.y"),
                   range = RANGE::lit(key="height")
                   ,nice = true
                 ),
                 scale(name="color", \type = "ordinal",
                   range = RANGE::lit(key="category10")
                 )
                ]
               ,
              \data =[datum(name="table")]
             ,
             padding= padding(left=100, bottom = 100, top = 50, right = 10),
             marks = [mark(\type = "group",
                        from = datum(\data="table",
                                      transform = [
                                          transform(keys = ["data.x"],
                                          \type = "facet"
                                     )
                                    ] 
                                )
                            , 
                         scales =  [
                         scale(name="c", \type = "ordinal", 
                                   domain = DOMAIN::ref(\data = "table", field = "data.c"),
                                   range = RANGE::lit(key= "width"))             
                                   ],
                          properties = (
                                     "enter":(
                                        "x":("scale":"x", "field":"key")
                                        ,"width": ("scale":"x", "band":true))
                                     ),
                         marks=[
                             mark(\type="rect",
                                  properties = (
                                     "enter":(
                                      
                                      "fill":("scale":"color", "field":"data.c"),
                                      "width":("scale":"c","band":true),
                                      "x":("scale":"c","field":"data.c"),
                                      "y":("scale":"y","field":"data.y"),
                                      "y2":("scale":"y","value":0)
                                      ),
                                     "update":  ("fillOpacity":("value":1.0)),
                                     "hover":   ("fillOpacity":("value":0.5))   
                                  )
                               
                                ),
                                
          mark(\type= "text",
                  properties = 
                    ("enter": (
                              "x": ("scale": "c", "field": "data.c"),
                              "dx": ("scale": "c", "band": true, "mult": 0.5),
                               "y": ("scale": "y", "field": "data.y", "offset": -10),
                               "fill": ("value": "black"),
                               "fillOpacity":("value":0),
                                "align": ("value": "right"),
                                "baseline": ("value": "bottom"),
                                "text": ("field": "data.y"),
                                "angle":("value":90)
                             ),
                       "update":  ("fillOpacity":("value":0)),
                       "hover":   ("fillOpacity":("value":1))  
                           )              
                         )
                      ]                                    
                     )
                   ]         
                ); 
             
public VEGA() groupedBar(bool grid = false, 
    map[str, str] title = (), map[str, str] legends = ()
    ,map[str, TICKLABELS] tickLabels = ()
    , list[str] palette = []
    , map[str, str] \format = (), map[str, int] ticks = (), map[str, list[str]] values = ()
    ) {
    return VEGA() {
        return update(_groupedBar, grid = grid, title = title, legends = legends
        ,tickLabels = tickLabels,  palette = palette
        , \format = \format, ticks = ticks, values = values
        );
        };
    }
    
VEGA  _linePlot = 
        vega(
            viewport = [1800, 1800]       
           ,axes= [
                axe(scale="x", \type="x", 
                  tickSize = 0, tickPadding = 8
                ),
                axe(scale = "y", \type ="y"              
                )
                ]
           ,scales = 
                [scale(name="x", \type = "linear", 
                   domain = DOMAIN::ref(\data = "table", field = "data.x")
                  ,range = RANGE::lit(key= "width")
                 ),
                 scale(name = "y", \type = "linear",
                   domain = DOMAIN::ref(\data="table", field = "data.y")
                   ,range = RANGE::lit(key="height")
                  ,nice = true
                 ),
                 scale(name="color", \type = "ordinal"
                      ,range = RANGE::lit(key="category10")
                 )
                ]
              ,\data =[datum(name="table")]
             ,padding= padding(left=100, bottom = 30, top = 10, right = 10)
             ,marks = [mark(\type = "group"
                     ,from = datum(\data="table",
                                      transform = [
                                        transform(keys = ["data.c"],
                                         \type = "facet"
                                          )               
                                         ] 
                                    )                          
                        ,scales =  [
                            scale(name="c", \type = "ordinal", 
                                   domain = DOMAIN::ref(\data = "table", field = "data.c"),
                                   range = RANGE::lit(key= "width"))             
                         ]
                        ,marks=[             
                              mark(\type="line" 
                                  , properties = (
                                     "enter":(    
                                      "stroke":("scale":"color", "field":"data.c")
                                      ,"x":("scale":"x","field":"data.x")
                                      ,"y":("scale":"y","field":"data.y")
                                      , "interpolate":("value":"monotone")
                                     )
                                     ,"update":  ("fillOpacity":("value":1.0))
                                     ,"hover":   ("fillOpacity":("value":0.5))   
                                  )              
                                )
                             ]                                        
                          )
                          
                   ]             
                ); 
             
        
  public VEGA() linePlot(bool grid = false, 
    map[str, str] title = (), map[str, str] legends = ()
    ,list[str] palette = []
    ,map[str, TICKLABELS] tickLabels =  ()
    ,map[str, str] \format = (), map[str, int] ticks = (), map[str, list[str]] values = () 
    ,map[str, str] interpolate = (),map[str, str] shape = () 
    ) {
    return VEGA() {
        return update(_linePlot, grid = grid, title = title
         ,legends = legends
         ,tickLabels = tickLabels,  palette = palette
         ,\format = \format, ticks = ticks, values = values
         ,interpolate = interpolate, shape = shape
        );
        };
    }
  