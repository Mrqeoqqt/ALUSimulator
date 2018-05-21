/**
 * ģ��ALU���������͸���������������
 * @author "151250177_Ѧ����"
 *
 */

public class ALU {

	/**
	 * ����ʮ���������Ķ����Ʋ����ʾ��<br/>
	 * ����integerRepresentation("9", 8)
	 * @param number ʮ������������Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʋ����ʾ�ĳ���
	 * @return number�Ķ����Ʋ����ʾ������Ϊlength
	 */
	public String integerRepresentation (String number, int length) {
		// TODO YOUR CODE HERE.
		int binary=1<<length-1;
		char[] D2BC=new char[length];
		int flag=0;
		if(number.charAt(0)=='-'){
			number=number.substring(1);
			flag=1;
		}
		int num=Integer.parseInt(number);
		
		int j=0;
		if(!((flag==1&&num==binary)||(num&binary)==0)){
			//���overflow,��ʾ��Χ-2^(length-1)~2^(length-1)-1
			return null;
		}else{
		for(;j<length;j++){
			if(num/Math.abs(binary)==0){
				D2BC[j]='0';
			}else if(num/Math.abs(binary)==1&&num%Math.abs(binary)==0){
				D2BC[j]='1';
				break;
			}else{
				D2BC[j]='1';
			}
			num=num-binary*(int)(D2BC[j]-'0');
			binary=binary>>>1;
		}
		for(j=j+1;j<length;j++){
			D2BC[j]='0';
	}
		//decimal2binary
		
		//�����������ȡ����1
		//���ұߵ�һ����Ϊ0��λ��ʼȡ��
		//����ұߵ�һλ��Ϊ0����ȡ����Ľ��0λ��Ϊ1�����򲻱�
		if(flag==1){
			int i=length-1;
			int count=0;
			for(;i>=0;i--){
				if(D2BC[i]=='0'){
					count++;
				}else{
					break;
				}
			}
			for(i=i-1;i>=0;i--){
				if(D2BC[i]=='1'){
					D2BC[i]='0';
				}else if(D2BC[i]=='0'){
					D2BC[i]='1';
			}
		}
			if(count==0){
				D2BC[length-1]='1';
			}
	}
		return String.valueOf(D2BC);
		}
	}
	
	
	
	
	/**
	 * ���һ����Ϊ�Ǹ���������ת�����޷�������
	 * @param number
	 * @param length
	 * @return
	 */
	public String integerUnsigned(String number,int length){
		String result=null;
		if(Integer.parseInt(number)>=0){
		result=integerRepresentation(number, length+1).substring(1);
		}
		return result;
	}
	/**
	 * ����һ������յķǸ�����
	 */
	public String integerUnsignedCompact(String number){
		StringBuilder result=new StringBuilder();
		if(number.charAt(0)!='-'){
			int i=0;
			int num=Integer.parseInt(number);
			while(num>=(1<<i)){
				i++;
			}
			i=i-1;
			for(int j=i;j>=0;j--){
				result.append(num/(1<<j));
				num=num%(1<<j);
			}
			return String.valueOf(result);
		}
		return null;
	}
	/**
	 * ����ʮ���Ƹ������Ķ����Ʊ�ʾ��
	 * ��Ҫ���� 0������񻯡����������+Inf���͡�-Inf������ NaN�����أ������� IEEE 754��
	 * �������Ϊ��0���롣<br/>
	 * ����floatRepresentation("11.375", 8, 11)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return number�Ķ����Ʊ�ʾ������Ϊ 1+eLength+sLength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
	 */
	public String floatRepresentation (String number, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		//��ʾ��Χ��+-0/0,positive/negative infinitive,|x|<=(2-2^sLength)*(2^(eLength-1)-1)
		
		//������������������null;
		if(eLength<4||sLength<4){
			return null ;
		}
		
		char[] Float=new char[1+eLength+sLength];	//����ֵ
		if(number.charAt(0)=='-'){
			Float[0]='1';
			number=number.substring(1);
		}else{
		    Float[0]='0';
		}
		//��¼����
		
		
		
        double num=Double.parseDouble(number);
		//�ж��Ƿ�overflow
		if(num>(2-Math.pow(2, -sLength))*(Math.pow(2, eLength-1)-1)){
			for(int i=0;i<eLength;i++){
				Float[i+1]='1';
			}
			for(int i=0;i<sLength;i++){
				Float[1+eLength+i]='0';
			}
			return String.valueOf(Float);
		}
		//�ж��Ƿ�underflow
		if(num<Math.pow(2,2-Math.pow(2,eLength-1))*Math.pow(2, -sLength)){
			for(int i=0;i<eLength+sLength;i++){
				Float[i+1]='0';
			}
			return String.valueOf(Float);
		}
		
		
		char[] e;
		char[] s=new char[sLength];
		
		for(int i=0;i<sLength;i++){
			s[i]='0';
		}
		//�ж��Ƿ���С����
		int pointPos=-1;
		for(int i=0;i<number.length();i++){
			if(number.charAt(i)=='.'){
				pointPos=i;
				break;
			}
		}
		char[] integer=null;//��¼��������
		char[] remainder=null;//��¼С������
		int remainderFlag=0;//�ж�С�������Ƿ�ȫΪ0
		if(pointPos>=0){
			//��С����
			//�ָ�
			integer=number.substring(0,pointPos).toCharArray();
			remainder=number.substring(pointPos+1).toCharArray();
			for(int i=0;i<remainder.length;i++){
				if(remainder[i]!='0'){
					remainderFlag=1;
					break;
				}
			}
		}else{
			//��С����
			//ֻ��¼��������
			integer=number.toCharArray();
		}
		if(pointPos<0||(pointPos>=0&&remainderFlag==0)){
			//��С�����С������ȫΪ0
			//�����������
			String resultS=integerUnsigned(String.valueOf(integer), sLength+1);
			//����ΪsLength+1�ı��ʽ
			int posOne=0;
			//��¼�����ҵ�һ��1���ֵ�λ��
			//�淶��
			for(int i=0;i<sLength+1;i++){
				if(resultS.charAt(i)=='1'){
					posOne=i;
					break;
				}
			}
			e=integerUnsigned(String.valueOf(sLength-posOne+(int)(Math.pow(2, eLength-1)-1)),eLength).toCharArray();
			for(int i=0;i<resultS.substring(posOne+1).length();i++){
			s[i]=resultS.substring(posOne+1).charAt(i);
			}
			return String.valueOf(Float[0]).concat(String.valueOf(e)).concat(String.valueOf(s));
		}
		
		
		//С�����ֲ�ȫΪ0
		if(num<=Math.pow(2, 2-Math.pow(2,eLength-1))*(2-Math.pow(2,-sLength))){
			//�����絫�㹻С��ָ��Ϊ0
			//��ָ��Ϊ2^(eLength-1)-1
			for(int i=0;i<eLength;i++){
				Float[i+1]='0';
			}
			StringBuilder result=new StringBuilder();
			String temp=String.valueOf(remainder);
			temp=multiBy2(temp,(int)(Math.pow(2, eLength-1)-2));
			if(temp.charAt(0)=='0'){
				temp=temp.substring(1);
			}
			for(int i=0;i<sLength;i++){
				temp=multiBy2(temp,1);
				if(temp.charAt(0)=='1'){
				   result.append(1);
				}else{
					result.append(0);
				}
				temp=temp.substring(1);
			}
			return String.valueOf(Float).trim()+String.valueOf(result);
			}else{
				//ָ����1~2^eLength-1��Χ��
				//����������/2������С��������*2����
				StringBuilder result=new StringBuilder(integerUnsignedCompact(String.valueOf(integer)));
				int Length=integerUnsignedCompact(String.valueOf(integer)).length();
				String temp=String.valueOf(remainder);
				for(int i=Length;i<=sLength;i++){
					temp=multiBy2(temp,1);
					if(temp.charAt(0)=='1'){
					   result.append(1);
					}else{
						result.append(0);
					}
					temp=temp.substring(1);
					}
				e=integerUnsigned(String.valueOf(Length-1+(int)Math.pow(2, eLength-1)-1),eLength).toCharArray();
				return String.valueOf(Float[0])+String.valueOf(e)+String.valueOf(result.substring(1));
	}
	}
	
	/**
	 * �Ը�����С�����ֽ��г�2����
	 */
	public String multiBy2(String number,int time){
		int length=number.length();
		StringBuilder num=new StringBuilder("0"+number);
		int c=0;
		for(int j=0;j<time;j++){
        for(int i=length;i>0;i--){
        	int temp=(num.charAt(i)-'0')*2;
        		num.replace(i, i+1, String.valueOf(temp%10+c));
        		c=temp/10;
        }
        num.replace(0, 1,String.valueOf(c));
	}
		return String.valueOf(num);
	}
	
	/**
	 * ����ʮ���Ƹ�������IEEE 754��ʾ��Ҫ�����{@link #floatRepresentation(String, int, int) floatRepresentation}ʵ�֡�<br/>
	 * ����ieee754("11.375", 32)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʊ�ʾ�ĳ��ȣ�Ϊ32��64
	 * @return number��IEEE 754��ʾ������Ϊlength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
	 */
	public String ieee754 (String number, int length) {
		// TODO YOUR CODE HERE.
		String result;
		if(length==32){
			 result=floatRepresentation(number, 8, 23);
		}else if(length==64){
			 result=floatRepresentation(number, 11, 52);
		}else{
		     return null;
		}
		return result;
	}
	
	
 	
	/**
	 * ��������Ʋ����ʾ����������ֵ��<br/>
	 * ����integerTrueValue("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 */
	public String integerTrueValue (String operand) {
		// TODO YOUR CODE HERE.
		char[] Operand=operand.toCharArray();
		int trueValue=-(int)Math.pow(2, operand.length()-1)*(Operand[0]-'0');
		for(int i=1;i<operand.length();i++){
			trueValue+=(int)Math.pow(2, operand.length()-1-i)*(Operand[i]-'0');
		}
		return String.valueOf(trueValue);
	}
	
	/**
	 * ���������ԭ���ʾ�ĸ���������ֵ��<br/>
	 * ����floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ����������ֱ��ʾΪ��+Inf���͡�-Inf���� NaN��ʾΪ��NaN��
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		if(eLength<4||sLength<4){
			return null;
		}
		char sign=operand.charAt(0);
		int expo=Integer.parseInt(integerTrueValue("0".concat(operand.substring(1, eLength+1))));
		//ָ��
		int trueExpo=expo-((int)Math.pow(2, eLength-1)-1);
		//��ָ����ȥ2^(eLength-1)-1
		char[] value=operand.substring(1+eLength).toCharArray();
		double result;
		if(expo==(int)Math.pow(2, eLength)-1){
			int flag=0;
			for(int i=0;i<value.length;i++){
				if(value[i]=='1'){
					flag=1;
					break;
				}
			}
			if(flag==0){
				if(sign=='0'){
					return "+Inf";
				}else{
					return "-Inf";
				}
			}else{
				return "NaN";
			}
		}
		if(expo==0){
			int flag=0;
			for(int i=0;i<value.length;i++){
				if(value[i]=='1'){
					flag=1;
					break;
				}
			}
			if(flag==0){
				return "0";
			}else{
				trueExpo=(int)(2-Math.pow(2, eLength-1));
				result=Integer.parseInt(integerTrueValue("0".concat(String.valueOf(value))))*Math.pow(2, trueExpo-sLength);
				if(sign=='0'){
				return String.valueOf(result);
				}else{
				return String.valueOf(-result);
				}
			}
		}else if(expo>0&&expo<(int)Math.pow(2, eLength)-1){
			result=Integer.parseInt(integerTrueValue("01".concat(String.valueOf(value))))*Math.pow(2, trueExpo-sLength);
			if(sign=='0'){
			return String.valueOf(result);
			}else{
				return String.valueOf(-result);
			}
		}
		return null;
	}
	
	/**
	 * ��λȡ��������<br/>
	 * ����negation("00001001")
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @return operand��λȡ���Ľ��
	 */
	public String negation (String operand) {
		// TODO YOUR CODE HERE.
		char[] Operand=operand.toCharArray();
		for(int i=0;i<operand.length();i++){
			if(Operand[i]=='0'){
				Operand[i]='1';
			}else if(Operand[i]=='1'){
				Operand[i]='0';
			}
		}
		return String.valueOf(Operand);
	}
	
	/**
	 * ���Ʋ�����<br/>
	 * ����leftShift("00001001", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand����nλ�Ľ��
	 */
	public String leftShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		char[] Operand=operand.toCharArray();
		for(int i=0;i<n;i++){
			int j=0;
			for(;j<operand.length()-1;j++){
				Operand[j]=Operand[j+1];
			}
			Operand[j]='0';
		}
		return String.valueOf(Operand);
	}
	
	/**
	 * �߼����Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand�߼�����nλ�Ľ��
	 */
	public String logRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		char[] Operand=operand.toCharArray();
		for(int i=0;i<n;i++){
			int j=operand.length()-1;
			for(;j>0;j--){
				Operand[j]=Operand[j-1];
			}
			Operand[j]='0';
		}
		return String.valueOf(Operand);
	}
	
	/**
	 * �������Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand��������nλ�Ľ��
	 */
	public String ariRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		int flag=0;
		if(operand.charAt(0)=='1'){
			flag=1;
		}
		char[] Operand=operand.toCharArray();
		for(int i=0;i<n;i++){
			int j=operand.length()-1;
			for(;j>0;j--){
				Operand[j]=Operand[j-1];
			}
			if(flag==0){
			Operand[j]='0';
			}else{
				Operand[j]='1';
			}
		}
		return String.valueOf(Operand);
	}
	
	/**
	 * ȫ����������λ�Լ���λ���мӷ����㡣<br/>
	 * ����fullAdder('1', '1', '0')
	 * @param x ��������ĳһλ��ȡ0��1
	 * @param y ������ĳһλ��ȡ0��1
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ��ӵĽ�����ó���Ϊ2���ַ�����ʾ����1λ��ʾ��λ����2λ��ʾ��
	 */
	public String fullAdder (char x, char y, char c) {
		// TODO YOUR CODE HERE.
		int num1=x-'0';
		int num2=y-'0';
		int num3=c-'0';
		//num1������,num2����,num3��λ
		char[] str=new char[2];
		if(((num1^num2)^num3)==1){
			//������1
			if((num1&num2&num3)==0){
				//��ȫΪ1
				str[0]='0';
				str[1]='1';
			}else{
				//ȫΪ1
				str[0]='1';
				str[1]='1';
			}
		}else if(((num1^num2)^num3)==0){
			//ż����1
			if((num1|num2|num3)==0){
				//ȫΪ0
				str[0]='0';
				str[1]='0';
			}else{
				//��ȫΪ0
				str[0]='1';
				str[1]='0';
			}
		}
		return String.valueOf(str);
	}
	
	/**
	 * 4λ���н�λ�ӷ�����Ҫ�����{@link #fullAdder(char, char, char) fullAdder}��ʵ��<br/>
	 * ����claAdder("1001", "0001", '1')
	 * @param operand1 4λ�����Ʊ�ʾ�ı�����
	 * @param operand2 4λ�����Ʊ�ʾ�ļ���
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ����Ϊ5���ַ�����ʾ�ļ����������е�1λ�����λ��λ����4λ����ӽ�������н�λ��������ѭ�����
	 */
	public String claAdder (String operand1, String operand2, char c) {
		// TODO YOUR CODE HERE.
		/*Gi=Xi��Yi
		 * Pi=Xi+Yi
		 * Si=Gi^Pi^Ci
		 * Ci+1=Gi+Pi��Ci
		 * C0=Cin
		 */
		
		
		if(operand1.length()!=4||operand2.length()!=4){
			return null;
		}
		char[] Operand1=operand1.toCharArray();
		char[] Operand2=operand2.toCharArray();
		char[] result=new char[5];
		char[] C=new char[5];
		C[0]=c;
		for(int i=1;i<5;i++){
			C[i]=(char)((((Operand1[4-i]-'0')&(Operand2[4-i]-'0'))|(((Operand1[4-i]-'0')|(Operand2[4-i]-'0'))&(C[i-1]-'0')))+'0');
		}
		for(int i=4;i>0;i--){
			result[i]=(char)(((Operand1[i-1]-'0')&(Operand2[i-1]-'0'))^
					((Operand1[i-1]-'0')|(Operand2[i-1]-'0'))^(C[4-i]-'0')+'0');
		}
		result[0]=C[4];
		return String.valueOf(result);
	}
	
	/**
	 * ��һ����ʵ�ֲ�������1�����㡣
	 * ��Ҫ�������š����š�����ŵ�ģ�⣬
	 * ������ֱ�ӵ���{@link #fullAdder(char, char, char) fullAdder}��
	 * {@link #claAdder(String, String, char) claAdder}��
	 * {@link #adder(String, String, char, int) adder}��
	 * {@link #integerAddition(String, String, int) integerAddition}������<br/>
	 * ����oneAdder("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand��1�Ľ��������Ϊoperand�ĳ��ȼ�1�����е�1λָʾ�Ƿ���������Ϊ1������Ϊ0��������λΪ��ӽ��
	 */
	public String oneAdder (String operand) {
		// TODO YOUR CODE HERE.
		//���=(^p&q)|(p&^q)
		char[] result=new char[operand.length()+1];
		for(int i=operand.length();i>0;i--){
			result[i]=operand.charAt(i-1);
		}
		result[0]='0';
		for(int i=operand.length();i>=0;i--){
			if((((result[i]-'0')&0)|((~(result[i]-'0'))&1))==1){
				result[i]='1';
				break;
			}else{
				result[i]='0';
			}
		}
		return String.valueOf(result);
	}
	
	/**
	 * �ӷ�����Ҫ�����{@link #claAdder(String, String, char)}����ʵ�֡�<br/>
	 * ����adder("0100", "0011", ��0��, 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param c ���λ��λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
	 */
	
	public String adder (String operand1, String operand2, char c, int length) {
		// TODO YOUR CODE HERE.
		if(length%4!=0){
			return null;
		}
		String Operand1=fillLength(operand1,length);
		String Operand2=fillLength(operand2,length);
		char[] result=new char[length+1];
		int k=length/4-1;
		for(int i=k;i>=0;i--){
			char[] claAdderReturn=claAdder(Operand1.substring(4*i, 4*i+4),Operand2.substring(4*i, 4*i+4),c).toCharArray();
		    for(int j=4;j>0;j--){
		    	result[4*i+j]=claAdderReturn[j];
		    }
		    c=claAdderReturn[0];
		}
		result[0]=c;
		return String.valueOf(result);
	}
	
	/**
	 * ��ӷ���
	 * ��ȫ����
	 * @param operand
	 * @param length
	 * @return
	 */
	public String fillLength(String operand,int length){
		char[] result=new char[length];
		if(operand.length()<length){
			for(int i=operand.length()-1;i>=0;i--){
				result[length-operand.length()+i]=operand.charAt(i);
			}
		    char sign=operand.charAt(0);
			for(int i=0;i<length-operand.length();i++){
					result[i]=sign;
			}
			return String.valueOf(result);
		}else{
			return operand;
		}
		
	}
	
	
	/**
	 * ��ӷ���
	 * ��ȫ���ȣ�ԭ��
	 * @param operand
	 * @param length
	 * @return
	 */
	public String fillSignedLength(String operand,int length){
		char[] result=new char[length];
		char flag=operand.charAt(0);
		result[0]=flag;
		if(operand.length()<length){
			for(int i=operand.length()-1;i>0;i--){
				result[length-operand.length()+i]=operand.charAt(i);
			}
			for(int i=1;i<=length-operand.length();i++){
				result[i]='0';
			}
			return String.valueOf(result);
		}else{
			return operand;
		}
		
	}
	/**
	 * �����ӷ���Ҫ�����{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerAddition("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		if(length%4!=0){
			return null;
		}
		if(operand1.length()<length){
			operand1=fillLength(operand1, length);
		}
		if(operand2.length()<length){
			operand2=fillLength(operand2, length);
		}
		String result=adder(operand1,operand2,'0',length);
		if(operand1.charAt(0)!=operand2.charAt(0)){
			char[] temp=result.toCharArray();
			temp[0]='0';
			result=String.valueOf(temp);
		return result;
		}else{
			if(operand1.charAt(0)==result.charAt(1)){
				char[] temp=result.toCharArray();
				temp[0]='0';
				result=String.valueOf(temp);
				return result;
			}else{
				char[] temp=result.toCharArray();
				temp[0]='1';
				result=String.valueOf(temp);
				return result;
			}
		}
	}
	
	/**
	 * �����������ɵ���{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerSubtraction("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ��������
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		if(length%4!=0){
			return null;
		}
		if(operand1.length()<length){
			operand1=fillLength(operand1, length);
		}
		if(operand2.length()<length){
			operand2=fillLength(operand2, length);
		}
		String operand3=negation(operand2);//operand2ȡ������ͽ�λ��Ϊ��1��
		String result=adder(operand1,operand3,'1',length);
		if(operand1.charAt(0)==operand2.charAt(0)){
			char[] temp=result.toCharArray();
			temp[0]='0';
			result=String.valueOf(temp);
			return result;
		}else{
			if(operand1.charAt(0)!=result.charAt(1)){
			char[] temp=result.toCharArray();
			temp[0]='1';
			result=String.valueOf(temp);
			return result;
			}else{
				char[] temp=result.toCharArray();
				temp[0]='0';
				result=String.valueOf(temp);
				return result;
			}
		}
		
	}
	
	/**
	 * �����˷���ʹ��Booth�㷨ʵ�֣��ɵ���{@link #adder(String, String, char, int) adder}�ȷ�����<br/>
	 * ����integerMultiplication("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ����˽�������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����˽��
	 */
	public String integerMultiplication (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		if(length%4!=0||length<operand1.length()||length<operand2.length()){
			return null;;
		}
		int operand2L=operand2.length();
		operand2=fillLength(operand2,length);
		char[] result=new char[length+1];
		for(int i=operand2.length();i>0;i--){
			result[length-i]=operand2.charAt(operand2.length()-i);
		}
		for(int i=0;i<length-operand2.length();i++){
			result[i]='0';
		}
		result[length]='0';
		for(int i=0;i<operand2L;i++){
			if(result[length-1]-result[length]==0){
				for(int j=length;j>0;j--){
					result[j]=result[j-1];
				}
			}else if(result[length-1]-result[length]==-1){
				String subStr1=String.valueOf(result).substring(0,operand1.length());
				String subStr2=String.valueOf(result).substring(operand1.length(),length+1);
				result=(integerAddition(subStr1, operand1, operand1.length()).substring(1)+subStr2).toCharArray();
				for(int j=length;j>0;j--){
					result[j]=result[j-1];
				}
			}else if(result[length-1]-result[length]==1){
				String subStr1=String.valueOf(result).substring(0,operand1.length());
				String subStr2=String.valueOf(result).substring(operand1.length(),length+1);
				result=(integerSubtraction(subStr1, operand1, operand1.length()).substring(1)+subStr2).toCharArray();
				for(int j=length;j>0;j--){
					result[j]=result[j-1];
				}
			}
		}
		return String.valueOf(result);
	}
	
	/**
	 * �����Ĳ��ָ������������ɵ���{@link #adder(String, String, char, int) adder}�ȷ���ʵ�֡�<br/>
	 * ����integerDivision("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊ2*length+1���ַ�����ʾ�������������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0�������lengthλΪ�̣����lengthλΪ����
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		if(length<operand1.length()||length<operand2.length()){
			return null;
		}
		if(operand1.length()<length){
			operand1=fillLength(operand1, length);
		}
		if(operand2.length()<length){
			operand2=fillLength(operand2, length);
		}
		char[] result=(fillLength(operand1, 2*length)+"0").toCharArray();
		char[] signString;
		char flag1='0';
		char flag2='0';
		char sign;
		for(int i=length;i>=0;i--){
			if(result[0]==operand2.charAt(0)){
			signString=integerSubtraction(String.valueOf(result).substring(0, length),operand2,length).substring(1).toCharArray();
			sign=signString[0];
			flag1=integerSubtraction(String.valueOf(result).substring(0, length),operand2,length).charAt(0);
			if(flag1=='1'){
				break;
			}
			//�Ƿ񹻼�
			}else{
				signString=integerAddition(String.valueOf(result).substring(0, length),operand2,length).substring(1).toCharArray();
				sign=signString[0];
				flag1=integerAddition(String.valueOf(result).substring(0, length),operand2,length).charAt(0);
				if(flag1=='1'){
					break;
				}
			}
			
			if(result[0]==sign){
				//�뱻����ͬ�ţ�����
				if(result[0]!=operand2.charAt(0)){
				result[2*length]='0';
				}else{
				result[2*length]='1';
				}
			}else{
				if(result[0]!=operand2.charAt(0)){
					result[2*length]='1';
					}else{
					result[2*length]='0';
					}
			}
			for(int j=0;j<length;j++){
				result[j]=signString[j];
			}
			if(i!=0){
			result=leftShift(String.valueOf(result), 1).toCharArray();
			}
		}

//		if(operand2.charAt(0)==operand1.charAt(0)){
//			flag2=integerAddition(String.valueOf(result).substring(0, length), operand2, length).charAt(0);
//			signString=integerAddition(String.valueOf(result).substring(0, length), operand2, length).substring(1).toCharArray();
//			for(int j=0;j<length;j++){
//				result[j]=signString[j];
//			}
//		}else{
//			flag2=integerSubtraction(String.valueOf(result).substring(0, length), operand2, length).charAt(0);
//			signString=integerSubtraction(String.valueOf(result).substring(0, length), operand2, length).substring(1).toCharArray();
//			for(int j=0;j<length;j++){
//				result[j]=signString[j];
//			}
//		}
		int temp=0;
		//������������
		if(operand1.charAt(0)!=result[0]){
			if(operand1.charAt(0)=='1'){
				temp=-1;
				flag2=integerSubtraction(String.valueOf(result).substring(0, length), operand2, length).charAt(0);
				signString=integerSubtraction(String.valueOf(result).substring(0, length), operand2, length).substring(1).toCharArray();
				for(int j=0;j<length;j++){
					result[j]=signString[j];
				}
			}else{
				temp=1;
				flag2=integerAddition(String.valueOf(result).substring(0, length), operand2, length).charAt(0);
				signString=integerAddition(String.valueOf(result).substring(0, length), operand2, length).substring(1).toCharArray();
				for(int j=0;j<length;j++){
					result[j]=signString[j];
				}
			}
		}
		String quotient=new String();
		//���������������Ҳ��������
		if(temp==0){
			quotient=String.valueOf(result).substring(length+1,2*length+1);
		}else if(temp==1){
		    quotient=oneAdder(String.valueOf(result).substring(length+1,2*length+1)).substring(1);
		}else if(temp==-1){
			quotient=integerSubtraction(String.valueOf(result).substring(length+1,2*length+1),"1",length).substring(1);
		}
		String remainder=String.valueOf(result).substring(0,length);
		if(flag1=='1'||flag2=='1'){
			return "1"+quotient+remainder;
		}else{
			return "0"+quotient+remainder;

		}
		
	}
	
	/**
	 * �����������ӷ������Ե���{@link #adder(String, String, char, int) adder}�ȷ�����
	 * ������ֱ�ӽ�������ת��Ϊ�����ʹ��{@link #integerAddition(String, String, int) integerAddition}��
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}��ʵ�֡�<br/>
	 * ����signedAddition("1100", "1011", 8)
	 * @param operand1 ������ԭ���ʾ�ı����������е�1λΪ����λ
	 * @param operand2 ������ԭ���ʾ�ļ��������е�1λΪ����λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ����������ţ�����ĳ���������ĳ���С��lengthʱ����Ҫ���䳤����չ��length
	 * @return ����Ϊlength+2���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������2λΪ����λ����lengthλ����ӽ��
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		if(length%4!=0){
			return null;
		}
		if(!(length>=(operand1.length()-1)&&length>=(operand2.length()-1))){
			return null;
		}
	    operand1=fillSignedLength(operand1, length+1);
	    operand2=fillSignedLength(operand2, length+1);
	    char flag1=operand1.charAt(0);
		char flag2=operand2.charAt(0);
		StringBuilder result=new StringBuilder();
		char flag='0';
		if(flag1==flag2){
			flag=flag1;
			result.append(adder(operand1.substring(1),operand2.substring(1), '0', length));
			//������ӣ�������λΪ������ʾ�����������λ�������λΪ1��
			if(result.charAt(1)=='1'){
				result.replace(0, 1, "1");
			}
		}else{
			int differValue=Integer.parseInt("0"+integerTrueValue(operand1.substring(1)))-Integer.parseInt("0"+integerTrueValue(operand2.substring(1)));
			if(differValue==0){
				for(int i=0;i<=length+1;i++){
					result.append('0');
				}
				return String.valueOf(result);
				}
			if(differValue<0){
				flag=flag2;
			}else if(differValue>0){
				flag=flag1;
			}
			result.append(integerRepresentation(String.valueOf(Math.abs(differValue)), length+1));
			//����������
		}
		result.insert(1, flag);
		return String.valueOf(result);
	}
	
	/**
	 * �������ӷ����ɵ���{@link #signedAddition(String, String, int) signedAddition}�ȷ���ʵ�֡�<br/>
	 * ����floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����ӽ�������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		if(eLength<4||sLength<4){
			return null;
		}
		//expo1/expo2ȡ�����ֵ���򲻲������ֱ�ӷ���
		if(Integer.parseInt(integerTrueValue("0"+operand1.substring(1,eLength+1)))==(int)(Math.pow(2, eLength)-1)){
			return "1"+operand1;
		}
		if(Integer.parseInt(integerTrueValue("0"+operand2.substring(1,eLength+1)))==(int)(Math.pow(2, eLength)-1)){
			return "1"+operand2;
		}
		if(floatTrueValue(operand1,eLength,sLength)=="0"){
			return "0"+operand2;
		}
		if(floatTrueValue(operand2,eLength,sLength)=="0"){
			return "0"+operand1;
		}
		//����ֵ���ط���ֵ
		//һ����Ϊ0��������һ����
		//����Ƚ�exponent
		StringBuilder strGL=new StringBuilder();
		for(int i=0;i<gLength;i++){
			strGL.append(0);
		}
		StringBuilder Operand1=new StringBuilder(operand1.substring(1+eLength).concat(String.valueOf(strGL)));
		StringBuilder Operand2=new StringBuilder(operand2.substring(1+eLength).concat(String.valueOf(strGL)));
		//β��
		char flag1=operand1.charAt(0);
		char flag2=operand2.charAt(0);
		String expo1=Operand1.substring(1, eLength+1);
		String expo2=Operand2.substring(1, eLength+1);
		StringBuilder expo=new StringBuilder();
		for(int i=0;i<eLength;i++){
			expo.append(0);
		}
		int param1=0;
		int param2=0;
		StringBuilder result=new StringBuilder();
		if(Integer.parseInt(integerTrueValue("0"+expo1))==0&&Integer.parseInt(integerTrueValue(expo2))==0){
			param1=2-(int)Math.pow(2, eLength-1);
			param2=2-(int)Math.pow(2, eLength-1);
		}else if(Integer.parseInt(integerTrueValue("0"+expo1))!=0&&Integer.parseInt(integerTrueValue(expo2))==0){
			param1=Integer.parseInt(integerTrueValue("0"+expo1))-(int)(Math.pow(2, eLength-1)-1);
			param2=2-(int)Math.pow(2, eLength-1);
		}else if(Integer.parseInt(integerTrueValue("0"+expo1))==0&&Integer.parseInt(integerTrueValue(expo2))!=0){
			param1=2-(int)Math.pow(2, eLength-1);
			param2=Integer.parseInt(integerTrueValue("0"+expo1))-(int)(Math.pow(2, eLength-1)-1);
		}else{
			param1=Integer.parseInt(integerTrueValue("0"+expo1))-(int)(Math.pow(2, eLength-1)-1);
			param2=Integer.parseInt(integerTrueValue("0"+expo2))-(int)(Math.pow(2, eLength-1)-1);
		}
		//param1/param2Ϊ��ָ��
		int differValue=param1-param2;
//		if(differValue==0){
//			//���ն���ݹ鵽���ж��������ж�
//			//��ҪʹgLength������
//		    if(expo1.equals(String.valueOf(expo))){
//		    	String signedResult=signedAddition(flag1+"0"+Operand1.substring(eLength+1), flag2+"0"+Operand2.substring(eLength+1),sLength+gLength+1);
//		    	//��һλ�Ƿ�������ڶ�λ����λ
//		    	//ָ����Ϊ0ʱ����������
//		    	if(signedResult.charAt(2)=='0'){
//		    		result.append(0);
//		    		result.append(signedResult.charAt(1));
//		    		result.append(expo1);
//		    		result.append(signedResult.substring(3,sLength+3));
//		    		return String.valueOf(result);
//		    	}else if(signedResult.charAt(2)=='1'){
//		    		result.append(0);
//		    		param1++;
//		    		result.append(integerUnsigned(String.valueOf(param1),eLength));
//		    		result.append(signedResult.substring(3,sLength+3));
//		    		return String.valueOf(result);
//		    	}
//		    }else{
//		    	String signedResult=signedAddition(flag1+"1"+Operand1.substring(eLength+1), flag2+"1"+Operand2.substring(eLength+1),sLength+gLength+1);
//		    	if(signedResult.charAt(0)=='0'){
//		    		result.append(0);
//		    		result.append(signedResult.charAt(1));
//		    		result.append(expo1);
//		    		result.append(signedResult.substring(3,sLength+3));
//		    		return String.valueOf(result);
//		    	}else if(signedResult.charAt(0)=='1'){
//		    		param1++;
//		    		if(param1<=(int)(Math.pow(2, eLength)-2)-(int)(Math.pow(2, eLength-1)-1)){
//		    		result.append(0);
//		    		result.append(signedResult.charAt(1));
//		    		result.append(integerUnsigned(String.valueOf(param1), eLength));
//		    		result.append(signedResult.substring(2,sLength+2));
//		    		return String.valueOf(result);
//		    		}else{
//		    			result.append(1);
//		    			result.append(signedResult.charAt(1));
//		    			for(int i=0;i<eLength;i++){
//		    				result.append(1);
//		    			}
//		    			for(int i=0;i<sLength;i++){
//		    				result.append(0);
//		    			}
//		    			return String.valueOf(result);
//		    		}
//		    	}
//		    }
//	}
		if(differValue==0){
			if(expo1.equals(String.valueOf(expo))){
				Operand1.insert(0, 0);
				Operand2.insert(0, 0);
			}else{
				Operand1.insert(0, 1);
				Operand2.insert(0, 1);
			}
			if(flag1==flag2){
				Operand2=null;
				Operand2.append(integerAddition(negation(String.valueOf(Operand2)),integerRepresentation("1", sLength+gLength+1),sLength+gLength+1).substring(1));
			}
			String temp=integerAddition(String.valueOf(Operand1), String.valueOf(Operand2),sLength+eLength+1);
			if(integerTrueValue(temp)=="0"){
				return "0"+floatRepresentation("0", eLength, sLength);
			}else{
				
			}
		}else if(differValue>0){
			//����
			//��ʱgLength������
			if(expo2.equals(String.valueOf(expo))){
				operand2=operand2.charAt(0)+expo1+logRightShift("0"+operand1.substring(eLength+1),differValue).substring(1);
			}else{
				operand2=operand2.charAt(0)+expo1+logRightShift("1"+operand1.substring(eLength+1),differValue).substring(1);
			}
		}else if(differValue<0){
			String temp=operand2;
			operand2=operand1;
			operand1=temp;	
			result.append(floatAddition (operand1,operand2,eLength,sLength,gLength));
		}
		return null;
	}
	
	/**
	 * �������������ɵ���{@link #floatAddition(String, String, int, int, int) floatAddition}����ʵ�֡�<br/>
	 * ����floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ�������������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		if(operand2.charAt(0)=='0'){
			operand2="1"+operand2.substring(1);
		}else if(operand2.charAt(0)=='1'){
			operand2="0"+operand2.substring(1);
		}
		String result=floatSubtraction(operand1, operand2, eLength, sLength, gLength);
		return result;
	}
	
	/**
	 * �������˷����ɵ���{@link #integerMultiplication(String, String, int) integerMultiplication}�ȷ���ʵ�֡�<br/>
	 * ����floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		if(floatTrueValue(operand1, eLength, sLength)=="0"||floatTrueValue(operand2, eLength, sLength)=="0"){
			return "0"+floatRepresentation("0", eLength, sLength);
		}else if(floatTrueValue(operand1, eLength, sLength).equals("+Inf")){
			return "1"+operand1;
		}else if(floatTrueValue(operand2, eLength, sLength).equals("+Inf")){
			return "1"+operand2;
		}
		char flag1=operand1.charAt(0);
		char flag2=operand2.charAt(0);
		char flag;
		StringBuilder result=new StringBuilder();
		if(flag1==flag2){
			flag='0';
			result.append('0');
		}else{
			flag='1';
			result.append('1');
		}
		String expo1=operand1.substring(1, eLength+1);
		String expo2=operand2.substring(1, eLength+1);
		StringBuilder expo0=new StringBuilder();
		for(int i=0;i<eLength;i++){
			expo0.append(0);
		}
		String Operand1;
		String Operand2;
		int expo;
		if(expo1.equals(expo0)&&expo2.equals(expo0)){
			Operand1="0"+operand1.substring(1+eLength);
			Operand2="0"+operand2.substring(1+eLength);
			expo=Integer.parseInt
					(integerTrueValue(integerAddition("0"+expo1,"0"+expo2,((eLength+1)/4+1)*4)))
					-(int)(Math.pow(2,eLength-1)-2);
			//ָ��
		}else if(expo1.equals(expo0)&&!expo2.equals(expo0)){
			Operand1="0"+operand1.substring(1+eLength);
			Operand2="1"+operand2.substring(1+eLength);
			expo=Integer.parseInt
					(integerTrueValue(integerAddition("0"+expo1,
							"0"+expo2,((eLength+1)/4+1)*4)))
					-(int)(Math.pow(2,eLength-1)-2);
		}else if(expo2.equals(expo0)&&!expo1.equals(expo0)){
			Operand1="1"+operand1.substring(1+eLength);
			Operand2="0"+operand2.substring(1+eLength);
			expo=Integer.parseInt
					(integerTrueValue(integerAddition("0"+expo1,
							"0"+expo2,((eLength+1)/4+1)*4)))
					-(int)(Math.pow(2,eLength-1)-2);
		}else{
			Operand1="1"+operand1.substring(1+eLength);
			Operand2="1"+operand2.substring(1+eLength);
			expo=Integer.parseInt
					(integerTrueValue(integerAddition("0"+expo1,"0"+expo2,((eLength+1)/4+1)*4)))
					-(int)(Math.pow(2,eLength-1)-1);
		}
		
		if(expo>=(int)Math.pow(2, eLength)-1){
			//��������
		   for(int i=0;i<eLength;i++){
			   result.append(1);
		   }
		   for(int i=0;i<sLength;i++){
			   result.append(0);
		   }
		   result.insert(0, 1);
		   return String.valueOf(result);
		}else if(expo<0){
			//��������
			for(int i=0;i<eLength+sLength;i++){
				result.append(0);
			}
			result.insert(0, 1);
			return String.valueOf(result);
		}else{
			String intReturn=integerMultiplication(Operand1, Operand2, sLength+1);
			if(flag==intReturn.charAt(0)){
				String intResult=new String(intReturn.substring(1));
				if(expo<(int)Math.pow(2, eLength)-1){
					while(intResult.charAt(0)!='1'&&expo>=0){
						leftShift(intResult, 1);
						expo--;
					}
					result.append(integerUnsigned(String.valueOf(expo), eLength));
					result.append(intResult);
					result.insert(0, 0);
					return String.valueOf(result);
				}
				if(expo>=(int)Math.pow(2, eLength)-1){
					for(int i=0;i<eLength;i++){
						result.append(1);
					}
					for(int i=0;i<sLength;i++){
						result.append(0);
					}
					result.insert(0, 1);
					return String.valueOf(result);
				}
				if(expo<0){
					for(int i=0;i<eLength+sLength;i++){
						result.append(0);
					}
					result.insert(0, 1);
					return String.valueOf(result);
				}
			}else{
				String intResult=new String(intReturn.substring(1));
				intResult=integerAddition(negation(intResult),negation(integerRepresentation("1", sLength+1)), sLength+1);
				if(expo<(int)Math.pow(2, eLength)-1){
					while(intResult.charAt(0)!='1'&&expo>=0){
						leftShift(intResult, 1);
						expo--;
					}
					result.append(integerUnsigned(String.valueOf(expo), eLength));
					result.append(intResult);
					result.insert(0, 0);
					return String.valueOf(result);
				}
				if(expo>=(int)Math.pow(2, eLength)-1){
					for(int i=0;i<eLength;i++){
						result.append(1);
					}
					for(int i=0;i<sLength;i++){
						result.append(0);
					}
					result.insert(0, 1);
					return String.valueOf(result);
				}
				if(expo<0){
					for(int i=0;i<eLength+sLength;i++){
						result.append(0);
					}
					result.insert(0, 1);
					return String.valueOf(result);
				}
			}
		}
		return null;
	}
	
	/**
	 * �������������ɵ���{@link #integerDivision(String, String, int) integerDivision}�ȷ���ʵ�֡�<br/>
	 * ����floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
	public static void main(String[] args) {
		ALU alu=new ALU();
//		System.out.println(alu.floatRepresentation("0", 4,6));
//		System.out.println(alu.fillUnsignedLength("10001", 8));
//		System.out.println(alu.integerUnsignedCompact("100"));
//		System.out.println(alu.integerAddition("1100", "0001", 8));
//		System.out.println(alu.integerTrueValue("111100"));
//		System.out.println(alu.ariRightShift("111001010", 4));
//		System.out.println(alu.integerRepresentation("-8", 4));
//		System.out.println(alu.fillLength("1100", 8));
		
//	    System.out.println(alu.floatMultiplication("010001110000", "101110101100", 4, 7));//��������
		
		
		
		
//	    //��������ͨ��	
//		System.out.println(alu.ariRightShift("11110110", 2));
//		System.out.println(alu.logRightShift("11110110", 2));
//		System.out.println(alu.leftShift("00001001", 2));	
//		System.out.println(alu.fullAdder('1','1', '0'));
//		System.out.println(alu.claAdder("1001","0001", '1'));
//		System.out.println(alu.integerRepresentation("9", 8));
//		System.out.println(alu.adder("0100", "0011",'0',8));
//		System.out.println(alu.oneAdder("00001001"));
//		System.out.println(alu.negation("00001001"));
//		System.out.println(alu.integerAddition("0100", "0011", 8));
//		System.out.println(alu.integerSubtraction("0100", "0011", 8));
//		System.out.println(alu.integerMultiplication("0100", "0011", 8));
//		System.out.println(alu.floatRepresentation("11.375", 8, 11));
//		System.out.println(alu.ieee754("11.375", 32));
//		System.out.println(alu.integerTrueValue("00001001"));
//		System.out.println(alu.floatTrueValue("01000001001101100000", 8, 11));
//		System.out.println(alu.integerDivision("0100", "0011", 8));
//      System.out.println(alu.integerDivision("1001","0011",8));
//		System.out.println(alu.signedAddition("1100", "1011", 8));
//		//��������ͨ��


	}
}
