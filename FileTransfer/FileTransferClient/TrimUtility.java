class TrimUtility 
{
static public String trimPath(String path)
{
int i=0;
int len=path.length()-1;
String strin="";
while(i<=len)
{
System.out.println(path.charAt(i));
strin=strin+path.charAt(i);
i++;
}
return strin;
}
static public String trim(String path) {
String fileName="";
int j=path.length()-1;
int k=0;
while(path.charAt(j)!=92)
{
fileName=fileName+path.charAt(j);
//System.out.println(fileName[k]+","+j);
k++;
j--;
}
return (new StringBuffer(fileName).reverse().toString());
}
}