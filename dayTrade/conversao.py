# -*- coding: utf-8 -*-
"""
Created on Sun Jul 21 07:57:02 2019

@author: Felipe Fraga


"""

def funcao_retirar_null(elemento):
    conferencia = ['1','2','3','4','5','6','7','8','9','0','.',':',' ']
    tam = len(elemento)
    cont_elemento = 0
    
    resultado = ''
    
    while (cont_elemento < tam):
        cont_lista = 0
        while (cont_lista < 13):
            if (elemento[cont_elemento]==conferencia[cont_lista]):
                resultado += conferencia[cont_lista]
            cont_lista+=1
        cont_elemento+=1
    
    return resultado

lista = 'Instante;Abertura;Maior;Menor;Fechamento\n'

arq = open ('GGBR4M1.csv','r')

for linha in arq:
    aux = linha.split(',')
    if aux[0] == ' ' or aux[0] == '\n' or aux[0] == '':
        break
    cont = 0
    for elemento in aux:
        elemento=elemento.replace('ÿþ', '')
        elemento=elemento.replace('\n','')
        elemento = funcao_retirar_null(elemento)            
        lista+=elemento
        cont+=1
        if cont == 2:
            flutuante = float (elemento)
            print (flutuante)
        if cont < 5 and elemento != '':
            lista+=';'
        elif cont == 5 :
            lista+='\n'
            break
        

arq.close()
arq = open ('GerdauTratado1.csv', 'w')
arq.write(lista)
arq.close() 
        