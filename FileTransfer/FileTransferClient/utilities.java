class TMUtility
{
private TMUtility(){}
static public long bytesToLong(byte bytes[],int start,int end)
{
long g=0;
int i=0;
while(end>=start)
{
g=g+(((long)Math.pow(2,i))*bytes[end]);
i++;
end--;
}
return g;
}
static public byte[] longToBytes(long g)
{
byte bytes[]=new byte[64];
int i=63;
while(g>0)
{
if(g%2==0) bytes[i]=0;
else bytes[i]=1;
g=g/2;
i--;
}
return bytes;
}
}