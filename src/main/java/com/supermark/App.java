package com.supermark;

import static spark.Spark.*;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.models.Carrito;
import com.models.Comprobante;
//import com.models.Descuento;
import com.models.Detalle;
import com.models.Producto;
import com.models.TarjetaCredito;
import com.models.Usuario;
import com.supermark.services.CRUDCarrito;
import com.supermark.services.CRUDComprobante;
//import com.supermark.services.CRUDDescuento;
import com.supermark.services.CRUDProducto;
import com.supermark.services.CRUDTarjeta;
import com.supermark.services.CRUDusuario;



public class App {

	public static void main(String[] args) {
//------------------------------Usuario----------------------------------------------\\
		post("/registrar",(request,response)->{
			response.type("apliccation/json");
			Gson mapper = new Gson();
			Usuario user = mapper.fromJson(request.body(),Usuario.class);
			
			if(user.getContrasenia().length()<8) {
				System.out.println("Ingrese una contraseña de al menos 8 caracteres");
				return mapper.toJson(new StandardResponse(StatusResponse.ERROR,"Ingrese una contraseña de al menos 8 caracteres"));
			}else {
				CRUDusuario cu = new CRUDusuario();
				boolean resultado2 = cu.Verificacion_Reg(user);
				System.out.println(resultado2);
				if(resultado2==true) {
					return mapper.toJson(new StandardResponse(StatusResponse.ERROR,"El usuario ya existe en el sistema"));
					}else {
						boolean resultado = cu.registrar(user);
						if(resultado==true) {
							return mapper.toJson(new StandardResponse(StatusResponse.SUCCESS,"Usuario registrado"));
						}else {
							return mapper.toJson(new StandardResponse(StatusResponse.ERROR,"Asegurese de ingresar los datos correctamente"));
						}
					}
				}
		});
		
		get("/Login",(request,response)->{
			response.type("apliccation/json");
			Gson mapper = new Gson();
			Usuario user = mapper.fromJson(request.body(),Usuario.class);
			
			CRUDusuario cu = new CRUDusuario();
			boolean resultado = cu.Iniciarsesion(user);
			
			if(resultado==true) {
				return mapper.toJson(new StandardResponse(StatusResponse.SUCCESS,"Sesion iniciada"));
			}else{
				return mapper.toJson(new StandardResponse(StatusResponse.ERROR,"Correo o contraseña no validos"));
			}
			});
	
//------------------------------Carrito--------------------------------------------\\
		post("/seleccionar",(request,response)->{
			response.type("application/json");
			Gson mapper = new Gson();
			Carrito productoAgregado = mapper.fromJson(request.body(), Carrito.class);
			
			CRUDCarrito ccarr = new CRUDCarrito();
			if(ccarr.agregar(productoAgregado)) {
				return mapper
						.toJson(new StandardResponse(
									StatusResponse.SUCCESS,
									"Producto agregado al carrito"	
								));
			}else {
				return mapper
						.toJson(new StandardResponse(
									StatusResponse.ERROR,
									"Hubo un inconveniente agregado el producto"
								));
			}
		});
		get("/micarrito/:name",(request,response)->{
			response.type("application/json");
			Gson mapper = new Gson();
			
			CRUDCarrito ccarr = new CRUDCarrito();
			ArrayList<Detalle> miCarrito = ccarr.getListado(
						new Usuario(Integer.valueOf(request.params(":name")))
						);
			
			if(miCarrito!=null && miCarrito.size()>0) {
				return mapper
						.toJson(new StandardResponse(
									StatusResponse.SUCCESS,
									mapper.toJson(miCarrito)
								));
			}else {
				return mapper
						.toJson(new StandardResponse(
									StatusResponse.ERROR,
									"Tu carrito se encuentra vacio"
								));
			}
		});
//------------------------------Comprobante-----------------------------------------\\
		post("/registrarTar",(request,response)->{
			response.type("application/json");
			//response.header("Access-Control-Allow-Origin", "*");
			Gson mapper = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
			TarjetaCredito tar = mapper.fromJson(request.body(),TarjetaCredito.class);
			
			
				//Ejecutar un servicio
				CRUDTarjeta ct = new CRUDTarjeta();
				boolean resultado = ct.register(tar);
				System.out.println(tar);

			    if(resultado==true) {
			    	return mapper
			    			.toJson(new StandardResponse(
			    					StatusResponse.SUCCESS,
			    					"Tarjeta Registrad")
			    					);
			    }else {
			    	return mapper
			    			.toJson(new StandardResponse(
			    					StatusResponse.ERROR,
			    					"Ocurrio un error inesperado")
			    					);
			    }
		});
		
		post("/comprar",(request,response)->{
			response.type("application/json");
			//response.header("Access-Control-Allow-Origin", "*");
			Gson mapper = new Gson();
			//Gson mapper = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
			Comprobante comp = mapper.fromJson(request.body(),Comprobante.class);
			comp.setFecha(new Timestamp(System.currentTimeMillis()));
			
			CRUDComprobante cc = new CRUDComprobante();
						
			if(cc.registrarCompra(comp)) {
				return mapper
						.toJson(new StandardResponse(
								StatusResponse.SUCCESS,
								"Compra Registrada"
								));
			}else {
				return mapper
						.toJson(new StandardResponse(
								StatusResponse.ERROR,
								"Hubo un inconveniente al registrar su compra. Intente nuevamente, más tarde..."
								));
			}
		});
		get("/usuariocompro",(request,response)->{
			response.type("application/json");
			//response.header("Access-Control-Allow-Origin", "*");
			Gson mapper = new Gson();
			//Gson mapper = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
			Usuario us = mapper.fromJson(request.body(),Usuario.class);
			
			
			CRUDComprobante cc = new CRUDComprobante();
			ArrayList<Comprobante> compo = cc.getComprobantes(us);
			if(compo!=null && compo.size()>0) {
				return mapper
						.toJson(new StandardResponse(
									StatusResponse.SUCCESS,
									"El usuario realizo compras en la app"
								));
			}else {
				return mapper
						.toJson(new StandardResponse(
									StatusResponse.ERROR,
									"El usuario no realizo compras"
								));
			}
		});
//------------------------------Producto--------------------------------------------\\
//		post("/Ingresadesc",(request,response)->{
//			response.type("application/json");
//			//response.header("Access-Control-Allow-Origin", "*");
//			//Gson mapper = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
//			Gson mapper = new Gson();
//			Descuento Desc = mapper.fromJson(request.body(),Descuento.class);
//			System.out.println(Desc);
//			
//				//Ejecutar un servicio
//				CRUDDescuento cd = new CRUDDescuento();
//				boolean resultado = cd.register(Desc);
//				System.out.println(Desc);
//
//			    if(resultado==true) {
//			    	return mapper
//			    			.toJson(new StandardResponse(
//			    					StatusResponse.SUCCESS,
//			    					"Descuento Registrado")
//			    					);
//			    }else {
//			    	return mapper
//			    			.toJson(new StandardResponse(
//			    					StatusResponse.ERROR,
//			    					"Ocurrio un error inesperado")
//			    					);
//			    }
//		});
		put("/Actualizarprod",(request,response)->{
			response.type("application/json");
			//response.header("Access-Control-Allow-Origin", "*");
			Gson mapper = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
			Producto prod = mapper.fromJson(request.body(),Producto.class);
			System.out.println(prod);
			
				//Ejecutar un servicio
				CRUDProducto cp = new CRUDProducto();
				boolean resultado = cp.actualizarProducto(prod);
				System.out.println(prod);

			    if(resultado==true) {
			    	return mapper
			    			.toJson(new StandardResponse(
			    					StatusResponse.SUCCESS,
			    					"Producto Actualizado")
			    					);
			    }else {
			    	return mapper
			    			.toJson(new StandardResponse(
			    					StatusResponse.ERROR,
			    					"Ocurrio un error inesperado")
			    					);
			    }
		});
		post("/Ingresaprod",(request,response)->{
			response.type("application/json");
			//response.header("Access-Control-Allow-Origin", "*");
			Gson mapper = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
			Producto prod = mapper.fromJson(request.body(),Producto.class);
			System.out.println(prod);
			
				//Ejecutar un servicio
				CRUDProducto cp = new CRUDProducto();
				boolean resultado = cp.registrar(prod);
				System.out.println(prod);

			    if(resultado==true) {
			    	return mapper
			    			.toJson(new StandardResponse(
			    					StatusResponse.SUCCESS,
			    					"Producto Registrado")
			    					);
			    }else {
			    	return mapper
			    			.toJson(new StandardResponse(
			    					StatusResponse.ERROR,
			    					"Ocurrio un error inesperado")
			    					);
			    }
		});
	}
}
