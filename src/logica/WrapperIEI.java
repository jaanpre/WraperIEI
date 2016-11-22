package logica;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WrapperIEI {
	private String marca;
	
	private final static WrapperIEI instance = new WrapperIEI();

    public static WrapperIEI getInstance() {
        return instance;
    }

	public static void main(String[] args) {
		List<Movil> prueba = resultadosPCComp("LG");
		for(Movil m: prueba) {
            System.out.println(m.getModelo());
        }
	}

	public WrapperIEI () {}
	
	public static List<Movil> resultadosPCComp(String marca){
		String modelo;
		String precio;
		String disponibilidad;
		//Lista de móviles a devolver
		List<Movil> res = new ArrayList<Movil>();
		
		//Inicializacion del webdriver
		String exePath = System.getProperty("user.dir").toString()+"/data/geckodriver.exe";
		System.setProperty("webdriver.gecko.driver", exePath);
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("marionette", true);
		WebDriver driver = new FirefoxDriver(capabilities);
		driver.get("http://www.pccomponentes.com");
		
		//Buscar móviles
		String searchText="Móviles"+ "\n";
		WebElement searchInputBox=driver.findElement(By.xpath(".//*[@id='query']"));
		searchInputBox.sendKeys(searchText);
		searchInputBox.submit();
		
		//Esperar resultados de la busqueda
		WebDriverWait waiting = new WebDriverWait(driver, 10);
		waiting.until( ExpectedConditions.presenceOfElementLocated(By.id("resultados-busqueda") ) );
		
		//Scroll y hacer click en marcas
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,400)", "");
		WebElement elementoMarcas = driver.findElement(By.xpath(".//a[contains(.,'Marcas')]"));
		elementoMarcas.click();
		
		//Esperar a que se despliegue marcas
		waiting = new WebDriverWait(driver,5);
		waiting.until(new ExpectedCondition<Boolean>() {
	        public Boolean apply(WebDriver driver) {
	        	WebElement m = driver.findElement(By.xpath(".//a[contains(.,'Marcas')]"));
	        	String enabled = m.getAttribute("aria-expanded");
	        	if(enabled.equals("true")) 
	        		return true;
	        	else
	        		return false;
	        }
		});
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Buscar el elemento ver más
		WebElement elementoMas = driver.findElement(By.xpath(".//*[@id='acc-fil-0']/div/a"));
		elementoMas.click();
		jse.executeScript("window.scrollBy(0,200)", "");
		
		//Esperar a que se despliegue ver más
		waiting = new WebDriverWait(driver, 10);
		waiting.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
            	WebElement m = driver.findElement(By.xpath("//a[@class='columna-de-filtros__ver-mas']"));
            	String enabled = m.getAttribute("aria-expanded");
            	if(enabled.equals("true")) 
            		return true;
            	else
            		return false;
            }
		});
		
		//Desplazarse hasta el elemento y hacer click en el checkbox de la marca
		WebElement element = driver.findElement(By.xpath(".//*[@id='acc-fil-0']/div/*/*/*[contains(.,'"+marca+"')]"));
		jse.executeScript("arguments[0].scrollIntoView(false);", element);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		element.click();
		
		//Esperar a que se carguen los resultados
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Scroll hasta el final de la página y hacer click en ver más hasta que aparezcan todos los elementos
		jse.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
		ArrayList<WebElement> btnMoreCheck = (ArrayList<WebElement>) driver.findElements(By.xpath(".//Button[@style='display: none;']"));
		while(btnMoreCheck.size() == 0){
			WebElement btnMore = driver.findElement(By.xpath(".//*[@id='btnMore']"));
			btnMore.click();
			for (int second = 0; second<=150; second++) {
			    jse.executeScript("window.scrollTo(0, document.body.scrollHeight - 1)"); //y value '400' can be altered
			}
			btnMoreCheck = (ArrayList<WebElement>) driver.findElements(By.xpath(".//Button[@style='display: none;']"));
		}
		
		//Obtener todos los elementos que aparecen en la primera página
		ArrayList<WebElement> resultados2= (ArrayList<WebElement>) driver.findElements(By.xpath("//*[contains(@class, 'tarjeta-articulo expandible')]"));
		System.out.println("Resultados " + resultados2.size());
		//Iterar sobre la lista para obtener las características de los artículos
		WebElement actual_Elemento, navegacion2;
		for (int i=0; i< resultados2.size(); i++)
		{
			actual_Elemento = resultados2.get(i); // elemento actual de la lista.
			navegacion2 =actual_Elemento.findElement(By.xpath("./descendant::a"));
			modelo = navegacion2.getAttribute("data-name").toString();
			precio = navegacion2.getAttribute("data-price").toString();
			// si está disponible o no, se buscar en tarjeta-articulo__elementos-adicionales
			navegacion2 = actual_Elemento.findElement(By.className("tarjeta-articulo__disponibilidad"));
			disponibilidad = navegacion2.getText(); // el texto indica si está disponible o no
			res.add(new Movil("PCComponentes",modelo,precio+"€",disponibilidad));
		}
		driver.quit();
		return res;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	
}
