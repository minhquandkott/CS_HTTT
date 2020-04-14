import numpy
numpy.random.seed(4)
def MF(A, K, e = 0.1, steps = 5000):
    W = numpy.random.rand(user, K)
    H = numpy.random.rand(K, item)
    step = 0
    while(step < steps):
        for u in range(0, user):
            for i in range(0, item):
                if A[u][i] >0 :
                    r_bar =0
                    for k in range(K):
                        r_bar += numpy.dot(W[u][k], H[k][i])
                    eui = A[u][i] - r_bar
                    for k in range(K):
                        W[u][k] += 2*e*(eui* H[k][i])
                        H[k][i] += 2*e*(eui* W[u][k])
        step +=1
    return numpy.dot(W,H)

A = numpy.array([[1.75, 2.25, -0.5, -1.33, -1.5, 0, 0],
                [0.75, 0, 0, -1.33, 0, 0.5, 0],
                [0, 1.25, -1.5, 0, 0, -0.5, -2.33],
                [-1.25, -0.75, 0.5, 2.67, 1.5, 0, 0.67],
                [-1.25, -2.75, 1.5, 0, 0, 0, 1.67]])
print('A', A)

user = A.shape[0] # number user
item = A.shape[1] # numer item

MF =MF(A,3,0.01,1000)
Y =numpy.array([[round(MF[i][j]) for j in range(item)]for i in range(user)])
print('Ori Matrix:\n ', A)
print('MF Matrix:\n ', MF)
print('Round Matrix:\n',Y)