package aplicacao.model;

public class JwtToken {
	 private String sub;
	 private String name;
	 private float iat;


	 // Getter Methods 

	 public String getSub() {
	  return sub;
	 }

	 public String getName() {
	  return name;
	 }

	 public float getIat() {
	  return iat;
	 }

	 // Setter Methods 

	 public void setSub(String sub) {
	  this.sub = sub;
	 }

	 public void setName(String name) {
	  this.name = name;
	 }

	 public void setIat(float iat) {
	  this.iat = iat;
	 }
	}