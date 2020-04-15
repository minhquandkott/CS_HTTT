import numpy
numpy.random.seed(4)
def MFCTBi(A, K, beta=0.06, lan_da=0.03, loop=5000):
    u = numpy.sum(A) / (user * item - numpy.count_nonzero(A == 0))
    bs = numpy.arange(user, dtype='float')
    sum_rate_s = numpy.sum(A, axis=1)
    for s in range(user):
        ds = numpy.count_nonzero(A[s, :] > 0)
        bs[s] = (sum_rate_s[s] - ds * u) / ds

    bi = numpy.arange(item, dtype='float')
    sum_rate_i = numpy.sum(A, axis=0)
    for s in range(item):
        di = numpy.count_nonzero(A[:, s] > 0)
        bi[s] = (sum_rate_i[s] - di * u) / di

    W = numpy.random.rand(user, K)
    H = numpy.random.rand(K, item)

    step = 0
    while step < loop:
        for s in range(user):
            for i in range(item):
                if A[s][i] > 0:
                    psi = u + bs[s] + bi[i] + numpy.dot(W[s, :], H[:, i])
                    esi = A[s][i] - psi
                    u += (beta * esi)
                    bs[s] += beta * (esi - lan_da * bs[s])
                    bi[i] += beta * (esi - lan_da * bi[i])
                    for k in range(K):
                        W[s][k] += beta * \
                            (2 * esi * H[k][i] - lan_da * W[s][k])
                        H[k][i] += beta * \
                            (2 * esi * W[s][k] - lan_da * H[k][i])
        step += 1
    return W, H, bs, bi, u

A = numpy.array([[1.75, 2.25, -0.5, -1.33, -1.5, 0, 0],
                [0.75, 0, 0, -1.33, 0, 0.5, 0],
                [0, 1.25, -1.5, 0, 0, -0.5, -2.33],
                [-1.25, -0.75, 0.5, 2.67, 1.5, 0, 0.67],
                [-1.25, -2.75, 1.5, 0, 0, 0, 1.67]])

user = A.shape[0]
item = A.shape[1]

W1, H1, bs1, bi1, u1 = MFCTBi(A, 2)

Y = numpy.dot(W1, H1)
C = numpy.array([[round(Y[i][j]) for j in range(item)] for i in range(user)])

print('Ori Matrix:\n ', A)
print('MF Matrix:\n ', MFCTBi)
print('Round Matrix:\n',Y)