import java.util.List;
public class ASDR implements Parser{
    private int i = 0;
    private boolean hayErrores = false;
    private Token preanalisis;
    private final List<Token> tokens;
    public ASDR(List<Token> tokens){
        this.tokens = tokens;
        preanalisis = this.tokens.get(i);
    }
    @Override
    public boolean parse() {
        Q();
        if(preanalisis.tipo == TipoToken.EOF && !hayErrores){
            System.out.println("Consulta correcta");
            return  true;
        }else {
            System.out.println("Se encontraron errores");
        }
        return false;
    }
    private void Q(){
        match(TipoToken.SELECT);
        D();
        match(TipoToken.FROM);
        T();
    }
    private void T(){
        if(hayErrores)
            return;
        T2();
        T1();
    }
    private void T2(){
        if(preanalisis.tipo == TipoToken.IDENTIFICADOR){
            match(TipoToken.IDENTIFICADOR);
            T3();
        }
        else{
            hayErrores = true;
        }
    }
    private void T1(){
        if(hayErrores)
            return;
        if(preanalisis.tipo == TipoToken.COMA){
            match(TipoToken.COMA);
            T();
        }
    }
    private void T3(){
        if(hayErrores)
            return;
        if(preanalisis.tipo == TipoToken.IDENTIFICADOR){
            match(TipoToken.IDENTIFICADOR);
        }
    }
    private void D(){
        if(hayErrores)
            return;
        if(preanalisis.tipo == TipoToken.DISTINCT){
            match(TipoToken.DISTINCT);
            P();
        }
        else if (preanalisis.tipo == TipoToken.ASTERISCO
                || preanalisis.tipo == TipoToken.IDENTIFICADOR) {
            P();
        }
        else{
            hayErrores = true;
        }
    }
    private void P(){
        if(hayErrores)
            return;
        if(preanalisis.tipo == TipoToken.ASTERISCO){
            match(TipoToken.ASTERISCO);
        }
        else if(preanalisis.tipo == TipoToken.IDENTIFICADOR){
            A();
        }
        else{
            hayErrores = true;
        }
    }
    private void A(){
        if(hayErrores)
            return;
        A2();
        A1();
    }
    private void A2(){
        if(hayErrores)
            return;
        if(preanalisis.tipo == TipoToken.IDENTIFICADOR){
            match(TipoToken.IDENTIFICADOR);
            A3();
        }
        else{
            hayErrores = true;
        }
    }
    private void A1(){
        if(hayErrores)
            return;
        if(preanalisis.tipo == TipoToken.COMA){
            match(TipoToken.COMA);
            A();
        }
    }
    private void A3(){
        if(hayErrores)
            return;
        if(preanalisis.tipo == TipoToken.PUNTO){
            match(TipoToken.PUNTO);
            match(TipoToken.IDENTIFICADOR);
        }
    }
    
    private void match(TipoToken tt){
        if(preanalisis.tipo == tt){
            i++;
            preanalisis = tokens.get(i);
        }
        else{
            hayErrores = true;
        }

    }

}
