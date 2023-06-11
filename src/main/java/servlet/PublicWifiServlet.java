package servlet;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;

import com.google.gson.Gson;

import api.ApiLoader;
import database.DataBaseManager;
import database.SqliteManager;
import repository.BookmarkGroupRepository;
import repository.BookmarkRepository;
import repository.HistoryRepository;
import repository.WifiRepository;
import service.OpenAPILoaderService;

/**
 * Servlet implementation class PublicWifiServlet
 */
@WebServlet(urlPatterns = {"",
		"/addbookmark",
		"/addhistory",
		"/bookmark",
		"/bookmarkdetails",
		"/createbookmarkGroup",
		"/deletebookmark",
		"/deletebookmarkGroup",
		"/deletehistory",
		"/history",
		"/openAPI",
		"/updatebookmarkGroup",
		"/wifidetails"})
public class PublicWifiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private DataBaseManager dataBaseManager;
	private WifiRepository wifiRepository;
	private HistoryRepository historyRepository;
	private BookmarkGroupRepository bookmarkGroupRepository;
	private BookmarkRepository bookmarkRepository;
	private OpenAPILoaderService openAPILoaderService;
	private Gson gson;
	private ApiLoader apiLoader;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PublicWifiServlet() {
        super();
        this.dataBaseManager = new SqliteManager();
        this.wifiRepository = new WifiRepository(this.dataBaseManager);
        this.historyRepository = new HistoryRepository(this.dataBaseManager);
        this.bookmarkGroupRepository = new BookmarkGroupRepository(this.dataBaseManager);
        this.bookmarkRepository = new BookmarkRepository(this.dataBaseManager);
        
        this.gson = new Gson();
        this.apiLoader = new ApiLoader();
        this.openAPILoaderService = new OpenAPILoaderService(this.wifiRepository, this.gson, this.apiLoader);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String urlString = request.getRequestURI();
		
		request.setAttribute("wifiRepository", this.wifiRepository);
		request.setAttribute("historyRepository",this.historyRepository);
		request.setAttribute("bookmarkGroupRepository", this.bookmarkGroupRepository);
		request.setAttribute("bookmarkRepository", this.bookmarkRepository);
		request.setAttribute("openAPILoaderService", this.openAPILoaderService);
		
		if(Objects.equals(urlString, "/")) {
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}else {
			request.getRequestDispatcher(urlString+".jsp").forward(request, response);
		}
	}

}
