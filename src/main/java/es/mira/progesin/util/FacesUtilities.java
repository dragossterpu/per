package es.mira.progesin.util;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Component;

@Component("facesUtilities")
public class FacesUtilities {

	public void redirect(String pagina) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext extContext = ctx.getExternalContext();

		String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, pagina));

		try {
		     extContext.redirect(url);
		} catch (IOException ioe) {
		    throw new FacesException(ioe);
		}
	}
}
