package com.hswiggy.hibernate.operations;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.validation.Validation;

import com.hswiggy.hibernate.FoodItempojo;
import com.hswiggy.hibernate.Swiggypojo;

@Controller
public class Swiggyoperations {

	@RequestMapping(value = "/register", params = "regi", method = RequestMethod.POST)
	public String saveSwiggyuser(Swiggypojo pj, Model m) {

		
		
		Validation val = new Validation();
		boolean mobile = val.isValidMobile(pj.getMobile());
		
		if(mobile!=true) {
			
			
			m.addAttribute("mobile", "enter valid 10-digit number");
			return"index";
		}
		
		boolean mail = val.isValidMail(pj.getEmail());
		
		if(mail!=true) {
			
			m.addAttribute("mail", "enter a valid mail-id");
			return"index";
		}
		
		
		
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		session.save(pj);

		try {
			tx.commit();
		} catch (Exception e) {
			m.addAttribute("error", "the mailid alredy Exists...");
			return "index";
		}

		System.out.println("user saved succefully...");
		session.close();
		return "login";
	}

	@RequestMapping(value = "/loginuser", params = "action1", method = RequestMethod.POST)
	public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model m) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		String sql = "from Swiggypojo where email=:hi and password=:password";

		Query cq = session.createQuery(sql);

		cq.setParameter("hi", email);
		cq.setParameter("password", password);

		List lt = cq.list();

		if (lt.isEmpty()) {

			m.addAttribute("error", "try with valid credentilas");
			return "login";

		}

		return "orderfood";
	}

	@RequestMapping(value = "/loginuser", params = "action2", method = RequestMethod.POST)
	public String adminlogin(@RequestParam("email") String email, @RequestParam("password") String password, Model m) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		String sql = "from Swiggypojo where email=:hi and password=:password";

		Query cq = session.createQuery(sql);

		cq.setParameter("hi", email);
		cq.setParameter("password", password);

		List lt = cq.list();

		if (lt.isEmpty()) {

			m.addAttribute("error1", "you are no longer as admin");
			return "login";

		}

		return "addItem";
	}

	@RequestMapping(value = "/additem",params="add", method = RequestMethod.POST)
	public String saveFooditem(FoodItempojo fp, Model model) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		session.save(fp);

		tx.commit();

		session.close();
		model.addAttribute("message", fp.getItemname() + " has saved sussfully..!!");
		return "addItem";
	}

	@RequestMapping(value = "/additem",params="update", method = RequestMethod.POST)
	public String updateitem(Model m) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		String sql = "from FoodItempojo";
		Query res = session.createQuery(sql);

		List<FoodItempojo> menu = res.list();
		
		
		m.addAttribute("menu", menu);
		return "menu";
	}

	
	@RequestMapping(value = "/orderfood")
	public String orderFood(Model m) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		String sql = "from FoodItempojo";
		Query res = session.createQuery(sql);

		List<FoodItempojo> menu = res.list();

		for (FoodItempojo it : menu) {

			System.out.println(it.getItemname() + it.getPrice() + it.getQty() + it.getTypeofitem());
		}
		m.addAttribute("menu", menu);
		return "orderfood";
	}

	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public String forgotpassword(@RequestParam("email") String email, Model m, Swiggypojo pj) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		String sql = "from Swiggypojo  where email=:hi or password=:password  ";
		Transaction tx = session.beginTransaction();

		String pwd = "password";
		Query cq = session.createQuery(sql);

		cq.setParameter("hi", email);

		List lt = cq.list();
		if (lt.isEmpty()) {

			m.addAttribute("message", "email doesnot exits..");
			return "forgot";
		} else {
			m.addAttribute("message", "you are password is" + pwd);

			session.close();

			return "login";
		}
	}

	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)

	public String deleteitem(@RequestParam("itId") int itemid, Model m) {
		
		System.out.println("delete action raised...");
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		String sql="delete  FoodItempojo where itemid=:id";
		
		Query cq = session.createQuery(sql);
		
		cq.setParameter("id", itemid);
		
		int delete = cq.executeUpdate();
		tx.commit();
		
		
		
		if(delete==1) {
			
			String message = itemid + " deleted successfully!!";
			m.addAttribute("msg", message);
			
			
		}
		else {
			
			String message = itemid + " Failed...to deleted ";
			m.addAttribute("msg", message);
			
		}
		
		Query fecthmenu = session.createQuery("from FoodItempojo");
		
		List<FoodItempojo> menulist = fecthmenu.list();
		
		
		m.addAttribute("menu", menulist);
		
		session.close();
		return"menu";
	}
	
	
	@RequestMapping(value="/additem", params="users", method=RequestMethod.POST)
	public String userslist(Model m) {
		
		
		System.out.println("user menu trggerd...");
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		String sql = "from Swiggypojo";
		Query res = session.createQuery(sql);

		List<Swiggypojo> menu = res.list();
		
		m.addAttribute("menu", menu);
		return "userlist";

	}
	
	@RequestMapping(value="/orderfood",params="checked")
	public String checkeditem() {
		
		System.out.println("oredr chcked");
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		
		
		
		
		
		
		
		
		
		
		
		return"ordersuccess";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
