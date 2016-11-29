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
	private boolean pc,fnac;

	private final static WrapperIEI instance = new WrapperIEI();

	public static WrapperIEI getInstance() {
		return instance;
	}

	public List<Movil> resultadosTotales(String marca){
		List<Movil> res= new ArrayList<Movil>();
		if(pc&&fnac) res.addAll(devuelveIguales(resultadosPCComp(marca), resultadosFnac(marca)));
		else if(pc) res.addAll(resultadosPCComp(marca));
		else if(fnac) res.addAll(resultadosFnac(marca));
		//System.out.println(pc+""+fnac);
		return res;
	}

	public List<Movil> resultadosPCComp(String marca) {
		String modelo;
		String precio;
		String disponibilidad;
		// Lista de móviles a devolver
		List<Movil> res = new ArrayList<Movil>();

		// Inicializacion del webdriver
		String exePath = System.getProperty("user.dir").toString() + "/data/geckodriver.exe";
		System.setProperty("webdriver.gecko.driver", exePath);
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("marionette", true);
		WebDriver driver = new FirefoxDriver(capabilities);
		driver.get("http://www.pccomponentes.com");

		// Buscar móviles
		String searchText = "Móviles" + "\n";
		WebElement searchInputBox = driver.findElement(By.xpath(".//*[@id='query']"));
		searchInputBox.sendKeys(searchText);
		searchInputBox.submit();

		// Esperar resultados de la busqueda
		WebDriverWait waiting = new WebDriverWait(driver, 10);
		waiting.until(ExpectedConditions.presenceOfElementLocated(By.id("resultados-busqueda")));

		// Scroll y hacer click en marcas
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,400)", "");
		WebElement elementoMarcas = driver.findElement(By.xpath(".//a[contains(.,'Marcas')]"));
		elementoMarcas.click();

		// Esperar a que se despliegue marcas
		waiting = new WebDriverWait(driver, 5);
		waiting.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				WebElement m = driver.findElement(By.xpath(".//a[contains(.,'Marcas')]"));
				String enabled = m.getAttribute("aria-expanded");
				if (enabled.equals("true"))
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

		// Buscar el elemento ver más
		WebElement elementoMas = driver.findElement(By.xpath(".//*[@id='acc-fil-0']/div/a"));
		elementoMas.click();
		jse.executeScript("window.scrollBy(0,200)", "");

		// Esperar a que se despliegue ver más
		waiting = new WebDriverWait(driver, 10);
		waiting.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				WebElement m = driver.findElement(By.xpath("//a[@class='columna-de-filtros__ver-mas']"));
				String enabled = m.getAttribute("aria-expanded");
				if (enabled.equals("true"))
					return true;
				else
					return false;
			}
		});

		// Desplazarse hasta el elemento y hacer click en el checkbox de la
		// marca
		WebElement element;
		if(marca=="Lg")
		element = driver.findElement(By.xpath(".//*[@id='acc-fil-0']/div/*/*/*[contains(.,'LG')]"));
		else element = driver.findElement(By.xpath(".//*[@id='acc-fil-0']/div/*/*/*[contains(.,'" + marca + "')]"));
		jse.executeScript("arguments[0].scrollIntoView(false);", element);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		element.click();

		// Esperar a que se carguen los resultados
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Scroll hasta el final de la página y hacer click en ver más hasta que
		// aparezcan todos los elementos
		jse.executeScript(
				"window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
		ArrayList<WebElement> btnMoreCheck = (ArrayList<WebElement>) driver
				.findElements(By.xpath(".//Button[@style='display: none;']"));
		while (btnMoreCheck.size() == 0) {
			WebElement btnMore = driver.findElement(By.xpath(".//*[@id='btnMore']"));
			btnMore.click();
			for (int second = 0; second <= 150; second++) {
				jse.executeScript("window.scrollTo(0, document.body.scrollHeight - 1)"); 
			}
			btnMoreCheck = (ArrayList<WebElement>) driver.findElements(By.xpath(".//Button[@style='display: none;']"));
		}

		// Obtener todos los elementos que aparecen en la primera página
		ArrayList<WebElement> resultados2 = (ArrayList<WebElement>) driver
				.findElements(By.xpath("//*[contains(@class, 'tarjeta-articulo expandible')]"));
		System.out.println("Resultados " + resultados2.size());
		// Iterar sobre la lista para obtener las características de los
		// artículos
		WebElement actual_Elemento, navegacion2;
		for (int i = 0; i < resultados2.size(); i++) {
			actual_Elemento = resultados2.get(i); // elemento actual de la lista.
			navegacion2 = actual_Elemento.findElement(By.xpath("./descendant::a"));
			modelo = navegacion2.getAttribute("data-name").toString();
			precio = navegacion2.getAttribute("data-price").toString();
			// si está disponible o no, se buscar en
			// tarjeta-articulo__elementos-adicionales
			navegacion2 = actual_Elemento.findElement(By.className("tarjeta-articulo__disponibilidad"));
			disponibilidad = navegacion2.getText(); // el texto indica si está disponible o no
			res.add(new Movil("PCComponentes", modelo, precio + "€", disponibilidad));
		}
		driver.quit();
		return res;
	}

	public List<Movil> resultadosFnac(String marca) {
		String modelo;
		String precio;

		// Inicializacion del webdriver
		String exePath = System.getProperty("user.dir").toString() + "/data/geckodriver.exe";
		System.setProperty("webdriver.gecko.driver", exePath);
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("marionette", true);
		WebDriver driver = new FirefoxDriver(capabilities);
		driver.manage().window().maximize();
		driver.get("http://www.fnac.es");

		WebDriverWait waiting = new WebDriverWait(driver, 10);

		// Mover cursos a smartphones
		WebElement smartphones = driver.findElement(By.linkText("Smartphones y Conectados"));
		smartphones.click();

		waiting = new WebDriverWait(driver, 5);
		waiting.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Smartphones Libres")));

		WebElement smartphonesLibres = driver.findElement(By.linkText("Smartphones Libres"));
		smartphonesLibres.click();

		// Esperar resultados de la busqueda

		waiting.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Smartphones Asus")));

		// Elegir marca
		WebElement smartphonesMarca;
		if (marca == "Lg")
			smartphonesMarca = driver.findElement(By.linkText("Smartphones LG"));
		else
			smartphonesMarca = driver.findElement(By.linkText("Smartphones " + marca));
		smartphonesMarca.click();

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		// esperar
		waiting = new WebDriverWait(driver, 5);
		waiting.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("/html/body/div[4]/div/div[3]/div[2]/span[2]/ul/li[2]/span")));

		// Clickar en la marca para filtrar solo por marca
		WebElement marcaCheckBox = driver.findElement(By.xpath("//a[@data-noaccent='" + marca + "']"));
		marcaCheckBox.click();

		jse.executeScript(
				"window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");

		List<WebElement> articulos;
		List<Movil> moviles = new ArrayList<Movil>();
		WebElement actual, lista, siguiente;
		boolean esVisible;
		//por cada pagina
		do {
			try {
				WaitForAjax(driver);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Guardar datos
			lista = driver.findElement(By.xpath("//*[contains(@class, 'articleList')]"));
			articulos = lista.findElements(By.className("Article-itemGroup"));

			// Iterar sobre la lista para obtener las características de los
			// artículos
			for (int i = 0; i < articulos.size(); i++) {
				actual = articulos.get(i);

				if (actual.findElements(By.className("Nodispo")).size() < 1) {
					precio = actual.findElement(By.className("userPrice")).getText();

					modelo = actual.findElement(By.className("js-minifa-title")).getText();
					Movil m = new Movil("Fnac", modelo, null, null);
					m.setPrecio2(precio);
					moviles.add(m);
				}

			}
			//encontrar boton de siguiente pagina
			siguiente = driver.findElement(By.xpath("//a[@title='Página siguiente']"));
			WebElement parent = siguiente.findElement(By.xpath(".."));
			esVisible = !parent.getAttribute("class").contains("hide");
			
			//SI no es la ultima pagina
			if (esVisible)
				jse.executeScript("arguments[0].click();", siguiente);

		} while (esVisible);
		driver.quit();
		return moviles;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public static void WaitForAjax(WebDriver driver) throws InterruptedException {
		while (true) {
			Boolean ajaxIsComplete = (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active == 0");
			if (ajaxIsComplete) {
				break;
			}
			Thread.sleep(100);
		}
	}
	
	public void setBools(boolean pc, boolean fnac) {
		this.pc = pc;
		this.fnac = fnac;
	}
	
	public List<Movil> devuelveIguales(List<Movil> pccomp, List<Movil> fnac){
		List<Movil> res = new ArrayList<Movil>();
		Movil best;
		int temp;
		int max;
		for(Movil m1 : pccomp){
			max=0;
			best = null;
			for(Movil m2 : fnac){
				temp = comparadorModelos(m1.getModelo().split(" "),m2.getModelo().split(" "));
				if(temp > 3024 && temp > max){
					max = temp;
					best = m2;
				}else if(best!=null){
					if(temp==max && (Double.parseDouble(best.getPrecio2().replace("€", "").replace(".", "").replace(",", "."))+0.0)>(Double.parseDouble(m2.getPrecio2().replace("€", "").replace(".","").replace(",", "."))+0.0)) best = m2;
				}
			}
			if(best!=null){
				//System.out.println(m1.getModelo()+" es igual a: "+best.getModelo());
				m1.setPrecio2(best.getPrecio2());
				res.add(m1);
			}
		}
		return res;
	}
	
	public int comparadorModelos(String[] m1, String[] m2){
		int count = 0;
		int valor = 2048;
		for(String s : m1){
			for(String s2 : m2){
				if(s.toUpperCase().equals(s2.toUpperCase())){
					count+=valor;
					break;
				}
			}
			valor=valor/2;
		}
		return count;
	}
}
