module zoo::pico::syntax::Layout

layout PicoLayout = WhitespaceAndComment*;

syntax WhitespaceAndComment 
   = lex [\ \t\n\r]
   | lex "%" ![%]* "%"
   | lex "%%" ![\n]* "\n"
   ;